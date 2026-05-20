package work.utils;

import work.enums.DbType;
import work.model.dto.TableInsertData;
import work.model.entity.TableName;
import work.model.entity.TableRowData;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * INSERT语句生成工具
 * <ul>
 *     <li>目标MySQL：一张表输出一条批量INSERT语句</li>
 *     <li>目标Oracle：每条数据输出一条INSERT语句</li>
 *     <li>Oracle→MySQL：自动转换日期格式</li>
 * </ul>
 *
 * @since 2026-05-20
 */
public final class GenInsertScr {

    /** MySQL datetime格式化器 */
    private static final DateTimeFormatter MYSQL_DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /** MySQL date格式化器 */
    private static final DateTimeFormatter MYSQL_D_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 将数据导出为INSERT语句
     *
     * @param tableDataList 各表的数据结构列表
     * @param sqlName       输出的sql文件名
     * @param sourceDb      源数据库类型
     * @param targetDb      目标数据库类型
     */
    public static void genInsertSql(List<TableInsertData> tableDataList, String sqlName, DbType sourceDb, DbType targetDb) {
        System.out.printf("%n开始生成INSERT语句，从【%s】到【%s】%n", sourceDb, targetDb);
        StringBuilder result = new StringBuilder();

        for (TableInsertData tabData : tableDataList) {
            TableName tableNameObj = tabData.tableName();
            String rawTableName = tableNameObj.tableName().trim();
            List<List<TableRowData>> dataRows = tabData.rows();

            // 每张表上方输出注释（表名 + 中文注释）
            String comment = tableNameObj.comments();
            result.append("\n-- ").append(rawTableName);
            if (comment != null && !comment.isBlank()) {
                result.append("  ").append(comment.trim());
            }
            result.append("\n");

            if (dataRows.isEmpty()) {
                result.append("-- 表 ").append(rawTableName).append(" 无数据\n");
                continue;
            }

            if (targetDb == DbType.MySql) {
                result.append(buildMysqlBatchInsert(rawTableName, dataRows, sourceDb));
            } else {
                result.append(buildOracleInserts(rawTableName, dataRows, sourceDb));
            }
        }

        StrToFile.toFile(result.toString(), sqlName);
    }

    /**
     * MySQL版：一张表输出一条批量INSERT
     * INSERT INTO table_name (col1, col2, ...) VALUES (...), (...);
     */
    private static String buildMysqlBatchInsert(String tableName, List<List<TableRowData>> dataRows, DbType sourceDb) {
        // 从第一行数据中取字段名列表
        List<TableRowData> firstRow = dataRows.get(0);
        String cols = firstRow.stream()
                .map(rd -> rd.fieldName().toLowerCase())
                .collect(Collectors.joining(", "));

        String valuesPart = dataRows.stream()
                .map(row -> buildRowValues(row, sourceDb, DbType.MySql))
                .collect(Collectors.joining(",\n       "));

        return String.format("INSERT INTO %s (%s) VALUES\n       %s;\n",
                tableName.toLowerCase(), cols, valuesPart);
    }

    /**
     * Oracle版：每条数据一条INSERT
     * INSERT INTO TABLE_NAME (COL1, COL2, ...) VALUES (...);
     */
    private static String buildOracleInserts(String tableName,
                                              List<List<TableRowData>> dataRows,
                                              DbType sourceDb) {
        String tableNameUpper = tableName.toUpperCase();
        List<TableRowData> firstRow = dataRows.get(0);
        String cols = firstRow.stream()
                .map(rd -> rd.fieldName().toUpperCase())
                .collect(Collectors.joining(", "));

        StringBuilder sb = new StringBuilder();
        for (List<TableRowData> row : dataRows) {
            String values = buildRowValues(row, sourceDb, DbType.Oracle);
            sb.append(String.format("INSERT INTO %s (%s) VALUES %s;\n",
                    tableNameUpper, cols, values));
        }
        return sb.toString();
    }

    /**
     * 构建单行VALUES的括号内容，如 ('张三', 18, NULL, TO_DATE(...))
     * 直接从 TableRowData 中读取字段名、字段类型和值，无需额外传入 colTypeMap
     */
    private static String buildRowValues(List<TableRowData> row,
                                         DbType sourceDb,
                                         DbType targetDb) {
        String values = row.stream()
                .map(rd -> formatValue(rd.value(), rd.fieldType(), sourceDb, targetDb))
                .collect(Collectors.joining(", "));
        return "(" + values + ")";
    }

    /**
     * 将Java对象格式化为SQL字面量
     *
     * @param val      字段值
     * @param dataType 字段的数据库类型（如 DATE、VARCHAR2、NUMBER 等）
     * @param sourceDb 源数据库类型
     * @param targetDb 目标数据库类型
     */
    private static String formatValue(Object val, String dataType, DbType sourceDb, DbType targetDb) {
        if (val == null) {
            return "NULL";
        }

        String strVal = val.toString().trim();
        // 统一大写便于判断
        String typeUpper = dataType == null ? "" : dataType.trim().toUpperCase();

        // -------- 日期/时间类型处理 --------
        boolean isDateType = isDateTimeType(typeUpper);
        if (isDateType) {
            // Oracle→MySQL：Oracle的DATE/TIMESTAMP包含时分秒，转为MySQL datetime字符串
            if (sourceDb == DbType.Oracle && targetDb == DbType.MySql) {
                String mysqlDateStr = convertOracleDateToMysql(val, strVal);
                return "'" + mysqlDateStr + "'";
            }
            // MySQL→Oracle or Oracle→Oracle：用TO_DATE/TO_TIMESTAMP函数
            if (targetDb == DbType.Oracle) {
                return toOracleDateStr(val, strVal, typeUpper);
            }
            // MySQL→MySQL or 其他：直接用字符串格式
            String mysqlDateStr = toMysqlDateStr(val, strVal);
            return "'" + mysqlDateStr + "'";
        }

        // -------- 数字类型处理 --------
        if (isNumericType(typeUpper)) {
            if (strVal.matches("^-?\\d+(\\.\\d+)?$")) {
                return strVal;
            }
        }

        // Java数字类型直接返回（无需引号）
        if (val instanceof Number) {
            return strVal;
        }

        // -------- 字符串类型处理：转义单引号 --------
        String escaped = strVal.replace("'", "''");
        return "'" + escaped + "'";
    }

    // ====== 类型判断辅助方法 ======

    /** 判断是否为日期/时间类型 */
    private static boolean isDateTimeType(String dataType) {
        if (dataType == null || dataType.isBlank()) return false;
        return dataType.startsWith("DATE") || dataType.startsWith("TIMESTAMP")
                || dataType.equals("DATETIME") || dataType.equals("TIME");
    }

    /** 判断是否为数字类型 */
    private static boolean isNumericType(String dataType) {
        if (dataType == null || dataType.isBlank()) return false;
        return dataType.startsWith("NUMBER") || dataType.startsWith("INT") || dataType.startsWith("BIGINT")
                || dataType.startsWith("DECIMAL") || dataType.startsWith("NUMERIC")
                || dataType.startsWith("FLOAT") || dataType.startsWith("DOUBLE")
                || dataType.startsWith("TINYINT") || dataType.startsWith("SMALLINT");
    }

    // ====== 日期转换辅助方法 ======

    /**
     * Oracle DATE/TIMESTAMP → MySQL 日期字符串（yyyy-MM-dd HH:mm:ss）
     */
    private static String convertOracleDateToMysql(Object val, String strVal) {
        if (val instanceof LocalDateTime ldt) {
            return ldt.format(MYSQL_DT_FMT);
        }
        if (val instanceof LocalDate ld) {
            return ld.atStartOfDay().format(MYSQL_DT_FMT);
        }
        if (val instanceof Timestamp ts) {
            return ts.toLocalDateTime().format(MYSQL_DT_FMT);
        }
        if (val instanceof Date d) {
            return new Timestamp(d.getTime()).toLocalDateTime().format(MYSQL_DT_FMT);
        }
        return normalizeDateStr(strVal);
    }

    /**
     * 目标Oracle：返回 TO_DATE('...', 'YYYY-MM-DD HH24:MI:SS') 或 TO_TIMESTAMP(...)
     */
    private static String toOracleDateStr(Object val, String strVal, String dataType) {
        String dateStr;
        if (val instanceof LocalDateTime ldt) {
            dateStr = ldt.format(MYSQL_DT_FMT);
        } else if (val instanceof LocalDate ld) {
            dateStr = ld.atStartOfDay().format(MYSQL_DT_FMT);
        } else if (val instanceof Timestamp ts) {
            dateStr = ts.toLocalDateTime().format(MYSQL_DT_FMT);
        } else if (val instanceof Date d) {
            dateStr = new Timestamp(d.getTime()).toLocalDateTime().format(MYSQL_DT_FMT);
        } else {
            dateStr = normalizeDateStr(strVal);
        }
        if (dataType.startsWith("TIMESTAMP")) {
            return "TO_TIMESTAMP('" + dateStr + "', 'YYYY-MM-DD HH24:MI:SS')";
        }
        return "TO_DATE('" + dateStr + "', 'YYYY-MM-DD HH24:MI:SS')";
    }

    /**
     * 目标MySQL：返回 'yyyy-MM-dd HH:mm:ss' 或 'yyyy-MM-dd' 格式字符串
     */
    private static String toMysqlDateStr(Object val, String strVal) {
        if (val instanceof LocalDateTime ldt) {
            return ldt.format(MYSQL_DT_FMT);
        }
        if (val instanceof LocalDate ld) {
            return ld.format(MYSQL_D_FMT);
        }
        if (val instanceof Timestamp ts) {
            return ts.toLocalDateTime().format(MYSQL_DT_FMT);
        }
        if (val instanceof Date d) {
            return new Timestamp(d.getTime()).toLocalDateTime().format(MYSQL_DT_FMT);
        }
        return normalizeDateStr(strVal);
    }

    /**
     * 将字符串日期标准化为指定格式（容错处理）
     * 支持 Oracle/MySQL JDBC 常见输出格式：
     * - "2025-01-01 00:00:00.0"
     * - "2025-01-01 00:00:00"
     * - "2025-01-01"
     */
    private static String normalizeDateStr(String strVal) {
        if (strVal == null || strVal.isBlank()) return strVal;
        // 去掉末尾 ".0" 或 ".xxx"（Oracle/MySQL JDBC 附加的纳秒部分）
        String cleaned = strVal.replaceAll("\\.\\d+$", "").trim();
        try {
            if (cleaned.length() == 10) {
                LocalDate ld = LocalDate.parse(cleaned, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return ld.atStartOfDay().format(GenInsertScr.MYSQL_DT_FMT);
            }
            LocalDateTime ldt = LocalDateTime.parse(cleaned, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return ldt.format(GenInsertScr.MYSQL_DT_FMT);
        } catch (Exception e) {
            return cleaned;
        }
    }
}
