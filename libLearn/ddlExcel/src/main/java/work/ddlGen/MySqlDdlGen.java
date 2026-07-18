package work.ddlGen;

import work.enums.DbType;
import work.enums.JType;
import work.model.dto.TabXmlDo;
import work.model.entity.OriTabCol;
import work.model.entity.TableDDL;
import work.utils.StrToFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MySQL 建表语句生成器
 * <p>
 * 负责将源数据库字段转换为 MySQL 建表语句。
 * 字段类型转换路径：源类型 → 中间类型(MySQL) → MySQL类型（中间类型即MySQL，恒等）。
 *
 * @since 2026-07-09
 */
public class MySqlDdlGen implements DdlGen {

    private static final String DEFAULT_ENGINE = "InnoDB";

    @Override
    public void generate(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql) {
        DdlGen sourceConverter = DdlGenFactory.get(sourceDb);
        StringBuilder result = new StringBuilder();

        for (TabXmlDo tabXmlDo : tabXmlDos) {
            // 表名转小写
            String tableName = tabXmlDo.tableName().tableName().trim().toLowerCase();
            // 生成字段定义部分
            String columnsSql = tabXmlDo.tableDDLList().stream()
                    .map(ddl -> buildColumn(ddl, sourceConverter))
                    .collect(Collectors.joining(",\n  "));
            // 生成主键约束部分
            String primaryKeySql = buildPrimaryKey(tabXmlDo.tableDDLList());
            // 删表语句（存在即删，避免重复创建报错）
            String dropTableSql = addDropSql ? String.format("drop table if exists %s;\n", tableName) : "";
            // 拼接完整建表语句
            result.append(String.format("\n%screate table %s (\n  %s%s\n) comment='%s' engine=%s;\n",
                    dropTableSql,
                    tableName,
                    columnsSql,
                    primaryKeySql.isEmpty() ? "" : ",\n  " + primaryKeySql,
                    tabXmlDo.tableName().comments(),
                    DEFAULT_ENGINE
            ));
        }
        StrToFile.toFile(result.toString(), sqlName);
    }

    /**
     * 构建单个字段的SQL片段
     */
    private String buildColumn(TableDDL ddl, DdlGen sourceConverter) {
        // 字段名转小写
        String columnName = ddl.columnName().trim().toLowerCase();
        // 字段类型：源类型 → 中间类型(MySQL) → MySQL类型（MySQL即中间类型，恒等）
        String mysqlType = sourceConverter.toMiddleType(ddl.dataType()).trim().toLowerCase();
        // 非空约束
        String notNull = "Y".equalsIgnoreCase(ddl.isNotNull()) ? " not null" : "";
        // 字段注释（为空则不添加）
        String comment = ddl.comments() != null && !ddl.comments().isBlank()
                ? " comment '" + ddl.comments().trim() + "'"
                : "";
        // 默认值
        String defaultValue = buildDefaultVal(ddl.defaultVal(), mysqlType);
        return columnName + " " + mysqlType + defaultValue + notNull + comment;
    }

    /**
     * 构建主键约束SQL片段
     */
    private String buildPrimaryKey(List<TableDDL> ddlList) {
        List<String> primaryColumns = ddlList.stream()
                .filter(ddl -> "Y".equalsIgnoreCase(ddl.isPri()))
                .map(ddl -> ddl.columnName().trim().toLowerCase())
                .collect(Collectors.toList());

        if (primaryColumns.isEmpty()) {
            return "";
        }
        return "primary key (" + String.join(", ", primaryColumns) + ")";
    }

    /**
     * 解析默认值，生成DEFAULT语法片段
     */
    private String buildDefaultVal(String defaultValue, String mysqlType) {
        // 1. 无默认值（null/空字符串），直接返回空
        if (defaultValue == null || defaultValue.isBlank()) {
            return "";
        }
        String trimmedDefault = defaultValue.trim();
        // 2. 特殊默认值（无需加单引号，如函数、数值、布尔值）
        boolean isSpecialDefault =
                trimmedDefault.matches("^\\d+$")                              // 纯数字（如 0、100）
                        || trimmedDefault.matches("^\\d+\\.\\d+$")             // 小数（如 10.20）
                        || trimmedDefault.equalsIgnoreCase("CURRENT_TIMESTAMP")
                        || trimmedDefault.equalsIgnoreCase("NOW()")
                        || trimmedDefault.equalsIgnoreCase("NULL")
                        || trimmedDefault.equalsIgnoreCase("TRUE")
                        || trimmedDefault.equalsIgnoreCase("FALSE");
        if (isSpecialDefault) {
            return " default " + trimmedDefault;
        }
        // 3. 字符串类型默认值（需加单引号，同时转义内部单引号）
        boolean isStringType = mysqlType.startsWith("varchar")
                || mysqlType.startsWith("char")
                || mysqlType.contains("text");
        // 4. 日期时间类型默认值（若输入是字符串格式，需加单引号，如 '2025-01-01'）
        boolean isDateTimeType = mysqlType.equals("datetime");
        // Oracle的SYSDATE → MySQL的current_timestamp
        if (isDateTimeType && "SYSDATE".equals(trimmedDefault)) {
            return " default current_timestamp";
        }
        if (isStringType || isDateTimeType) {
            String escapedDefault = trimmedDefault.replace("'", "''");
            return " default '" + escapedDefault + "'";
        }
        // 5. 其他类型（如 TINYINT、DECIMAL 等），默认加单引号（兜底处理）
        return " default '" + trimmedDefault.replace("'", "''") + "'";
    }


    /**
     * MySQL 本身就是中间类型，直接返回原值
     */
    @Override
    public String toMiddleType(String nativeType) {
        return nativeType;
    }

    /**
     * MySQL 本身就是中间类型，直接返回原值
     */
    @Override
    public String fromMiddleType(String middleType) {
        return middleType;
    }

    // ==================== MySQL → Java（toJavaType）====================

    /**
     * MySQL字段类型 → Java类型
     */
    @Override
    public JType toJavaType(OriTabCol oriTabCol) {
        String pureType = oriTabCol.dataType().toUpperCase().trim();
        return switch (pureType) {
            // 整数类型
            case "INT", "INTEGER" -> JType.INT;
            case "BIGINT" -> JType.LONG;
            // 布尔类型
            case "TINYINT", "BOOLEAN" -> JType.BOOL;
            // 浮点/高精度类型
            case "FLOAT", "DOUBLE" -> JType.DOUBLE;
            case "DECIMAL", "NUMERIC" -> JType.DECIMAL;
            // 字符串类型
            case "VARCHAR", "CHAR", "TEXT", "LONGTEXT", "MEDIUMTEXT", "TINYTEXT" -> JType.STR;
            // 日期时间类型
            case "DATE" -> JType.DATE;
            case "TIME" -> JType.TIME;
            case "DATETIME", "TIMESTAMP" -> JType.DATE_TIME;
            // 二进制类型
            case "BLOB", "LONGBLOB", "MEDIUMBLOB", "TINYBLOB" -> JType.BYTE;
            // 兜底：未匹配的类型统一返回String
            default -> JType.STR;
        };
    }

    @Override
    public boolean insertBatch() {
        return true;
    }
}
