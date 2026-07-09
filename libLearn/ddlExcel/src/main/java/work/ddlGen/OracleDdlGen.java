package work.ddlGen;

import work.enums.DbType;
import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;
import work.utils.StrToFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Oracle 建表语句生成器
 * <p>
 * 负责将源数据库字段转换为 Oracle 建表语句。
 * 字段类型转换路径：源类型 → 中间类型(MySQL) → Oracle类型。
 *
 * @since 2026-07-09
 */
public class OracleDdlGen implements DdlGen, TypeConvert {

    @Override
    public void generate(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql) {
        TypeConvert sourceConverter = DdlGenFactory.getType(sourceDb);
        StringBuilder result = new StringBuilder();

        for (TabXmlDo tabXmlDo : tabXmlDos) {
            // 表名保持大写（Oracle习惯）
            String tableName = tabXmlDo.tableName().tableName().trim().toUpperCase();
            // 生成字段定义部分
            String columnsSql = tabXmlDo.tableDDLList().stream()
                    .map(ddl -> buildColumn(ddl, sourceDb, sourceConverter))
                    .collect(Collectors.joining(",\n  "));
            // 生成主键约束部分
            String primaryKeySql = buildPrimaryKey(tabXmlDo.tableDDLList());
            // 删表语句（存在即删）
            String dropTableSql = addDropSql ? String.format("DROP TABLE %s;\n", tableName) : "";
            // 拼接完整建表语句（Oracle不指定engine，使用COMMENT ON语句）
            result.append(String.format("\n%sCREATE TABLE %s (\n  %s%s\n);\n",
                    dropTableSql,
                    tableName,
                    columnsSql,
                    primaryKeySql.isEmpty() ? "" : ",\n  " + primaryKeySql
            ));
            // 添加表注释（Oracle使用单独的COMMENT语句）
            String tableComment = tabXmlDo.tableName().comments();
            if (tableComment != null && !tableComment.isBlank()) {
                result.append(String.format("COMMENT ON TABLE %s IS '%s';\n",
                        tableName,
                        tableComment.trim().replace("'", "''")
                ));
            }
            // 添加字段注释
            for (TableDDL ddl : tabXmlDo.tableDDLList()) {
                if (ddl.comments() != null && !ddl.comments().isBlank()) {
                    result.append(String.format("COMMENT ON COLUMN %s.%s IS '%s';\n",
                            tableName,
                            ddl.columnName().trim().toUpperCase(),
                            ddl.comments().trim().replace("'", "''")
                    ));
                }
            }
        }
        StrToFile.toFile(result.toString(), sqlName);
    }

    /**
     * 构建单个字段的SQL片段
     */
    private String buildColumn(TableDDL ddl, DbType sourceDb, TypeConvert sourceConverter) {
        // 字段名保持大写（Oracle习惯）
        String columnName = ddl.columnName().trim().toUpperCase();
        // 字段类型转换
        String oracleType;
        if (sourceDb == DbType.Oracle) {
            // 同库无需转换，直接使用原生类型
            oracleType = ddl.dataType().trim().toUpperCase();
        } else {
            // 跨库转换：源类型 → 中间类型(MySQL) → Oracle类型
            String middleType = sourceConverter.toMiddleType(ddl.dataType());
            oracleType = this.fromMiddleType(middleType).trim().toUpperCase();
        }
        // 非空约束
        String notNull = "Y".equalsIgnoreCase(ddl.isNotNull()) ? " NOT NULL" : "";
        // 默认值
        String defaultValue = buildDefaultVal(ddl.defaultVal(), oracleType);
        return columnName + " " + oracleType + defaultValue + notNull;
    }

    /**
     * 构建主键约束SQL片段
     */
    private String buildPrimaryKey(List<TableDDL> ddlList) {
        List<String> primaryColumns = ddlList.stream()
                .filter(ddl -> "Y".equalsIgnoreCase(ddl.isPri()))
                .map(ddl -> ddl.columnName().trim().toUpperCase())
                .collect(Collectors.toList());

        if (primaryColumns.isEmpty()) {
            return "";
        }
        return "PRIMARY KEY (" + String.join(", ", primaryColumns) + ")";
    }

    /**
     * 解析默认值，生成DEFAULT语法片段
     */
    private String buildDefaultVal(String defaultValue, String oracleType) {
        if (defaultValue == null || defaultValue.isBlank()) {
            return "";
        }
        String trimmedDefault = defaultValue.trim();
        // 特殊默认值（函数、数值、布尔值）
        boolean isSpecialDefault =
                trimmedDefault.matches("^\\d+$")                              // 纯数字
                        || trimmedDefault.matches("^\\d+\\.\\d+$")             // 小数
                        || trimmedDefault.equalsIgnoreCase("SYSDATE")
                        || trimmedDefault.equalsIgnoreCase("CURRENT_TIMESTAMP")
                        || trimmedDefault.equalsIgnoreCase("NULL")
                        || trimmedDefault.equalsIgnoreCase("TRUE")
                        || trimmedDefault.equalsIgnoreCase("FALSE");
        if (isSpecialDefault) {
            return " DEFAULT " + trimmedDefault;
        }
        // 字符串类型默认值（需加单引号）
        boolean isStringType = oracleType.startsWith("VARCHAR2")
                || oracleType.startsWith("CHAR")
                || oracleType.contains("CLOB");
        // 日期时间类型默认值
        boolean isDateTimeType = oracleType.equals("TIMESTAMP") || oracleType.equals("DATE");
        // MySQL的CURRENT_TIMESTAMP → Oracle的SYSDATE
        if (isDateTimeType && "CURRENT_TIMESTAMP".equalsIgnoreCase(trimmedDefault)) {
            return " DEFAULT SYSDATE";
        }
        if (isStringType || isDateTimeType) {
            String escapedDefault = trimmedDefault.replace("'", "''");
            return " DEFAULT '" + escapedDefault + "'";
        }
        // 其他类型兜底处理
        return " DEFAULT '" + trimmedDefault.replace("'", "''") + "'";
    }

    // ==================== Oracle → MySQL（toMiddleType）====================

    /**
     * Oracle → MySQL 字段类型映射
     */
    @Override
    public String toMiddleType(String nativeType) {
        String type = nativeType.trim().toUpperCase();

        // 字符串类型：VARCHAR2 / NVARCHAR2 → varchar(len)
        if (type.startsWith("VARCHAR2") || type.startsWith("NVARCHAR2")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "varchar(" + fieldLen + ")";
            }
            return "varchar(50)"; // 无长度时默认50
        }
        // 枚举类型：CHAR → char(len)
        if (type.startsWith("CHAR")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "char(" + fieldLen + ")";
            }
            return "char";
        }
        // 整数类型：NUMBER(p) → int(p≤10) / decimal(p)
        if (type.startsWith("NUMBER") && !type.contains(",")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                try {
                    int len = Integer.parseInt(fieldLen);
                    return len <= 10 ? "int" : "decimal(" + fieldLen + ")";
                } catch (NumberFormatException e) {
                    return "int";
                }
            }
            return "int";
        }
        // 小数类型：NUMBER(p,s) → decimal(p,s)
        if (type.startsWith("NUMBER") && type.contains(",")) {
            return "decimal" + type.substring(type.indexOf("("));
        }
        // 日期时间类型：DATE / TIMESTAMP → datetime
        if (type.equals("DATE") || type.equals("TIMESTAMP")) {
            return "datetime";
        }
        // 大文本类型：CLOB / LONG → text
        if (type.startsWith("CLOB") || type.equals("LONG")) {
            return "text";
        }
        // 二进制类型：BLOB → blob
        if (type.equals("BLOB")) {
            return "blob";
        }
        // 布尔类型：BOOLEAN → tinyint(1)
        if (type.equals("BOOLEAN")) {
            return "tinyint(1)";
        }
        // 未匹配到的类型默认 varchar(100)
        System.err.println("未匹配的类型：" + type);
        return "varchar(100)";
    }

    // ==================== MySQL → Oracle（fromMiddleType）====================

    /**
     * MySQL → Oracle 字段类型映射
     */
    @Override
    public String fromMiddleType(String middleType) {
        String type = middleType.trim().toUpperCase();

        // 字符串类型：VARCHAR / CHAR → VARCHAR2(len) / CHAR(len)
        if (type.startsWith("VARCHAR") || type.startsWith("CHAR")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                if (type.startsWith("CHAR")) {
                    return "CHAR(" + fieldLen + ")";
                }
                return "VARCHAR2(" + fieldLen + ")";
            }
            return "VARCHAR2(50)"; // 无长度时默认50
        }
        // 整数类型：INT → NUMBER(10)
        if (type.startsWith("INT")) {
            return "NUMBER(10)";
        }
        // 大整数类型：BIGINT → NUMBER(19)
        if (type.startsWith("BIGINT")) {
            return "NUMBER(19)";
        }
        // 小数类型：DECIMAL / DOUBLE / NUMERIC → NUMBER(p,s)
        if (type.startsWith("DECIMAL") || type.startsWith("DOUBLE") || type.startsWith("NUMERIC")) {
            return "NUMBER" + type.substring(type.indexOf("("));
        }
        // 日期时间类型：TIMESTAMP → TIMESTAMP
        if (type.equals("TIMESTAMP")) {
            return "TIMESTAMP";
        }
        // 日期时间类型：DATETIME / DATE → DATE
        if (type.equals("DATETIME") || type.equals("DATE")) {
            return "DATE";
        }
        // 大文本类型：TEXT / LONGTEXT → CLOB
        if (type.equals("TEXT") || type.equals("LONGTEXT")) {
            return "CLOB";
        }
        // 二进制类型：BLOB → BLOB
        if (type.equals("BLOB")) {
            return "BLOB";
        }
        // 布尔类型：TINYINT(1) → BOOLEAN
        if (type.startsWith("TINYINT(1)")) {
            return "BOOLEAN";
        }
        // 未匹配到的类型默认 VARCHAR2(100)
        System.err.println("未匹配的类型：" + type);
        return "VARCHAR2(100)";
    }
}
