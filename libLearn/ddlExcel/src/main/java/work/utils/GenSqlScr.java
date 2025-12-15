package work.utils;

import work.enums.DbType;
import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 建表语句生成工具
 *
 * @since 2025-11-26 09:13:12
 */
public class GenSqlScr {

    // Oracle → MySql 字段类型映射表（可根据实际需求扩展）
    private static String mapOracleTypeToMysql(String oracleDataType) {
        String type = oracleDataType.trim().toUpperCase();

        // 字符串类型
        if (type.startsWith("VARCHAR2") || type.startsWith("NVARCHAR2")) {
            // 提取长度（如 VARCHAR2(200) → 200）
            if (type.contains("(")) {
                @SuppressWarnings("DuplicateExpressions")
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "varchar(" + fieldLen + ")";
            }
            return "varchar(50)"; // 无长度时默认50
        }
        // 枚举类型
        else if (type.startsWith("CHAR")) {
            if (type.contains("(")) {
                @SuppressWarnings("DuplicateExpressions")
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "char(" + fieldLen + ")";
            }
            return "char";
        }
        // 整数类型
        else if (type.startsWith("NUMBER") && !type.contains(",")) {
            // NUMBER(p) → 区分 INT/BIGINT（p≤10用INT，否则BIGINT）
            if (type.contains("(")) {
                @SuppressWarnings("DuplicateExpressions")
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
        // 小数类型（NUMBER(p,s) → DECIMAL(p,s)）
        else if (type.startsWith("NUMBER") && type.contains(",")) {
            return "decimal" + type.substring(type.indexOf("("));
        }
        // 日期时间类型
        else if (type.equals("DATE") || type.equals("TIMESTAMP")) {
            return "datetime";
        }
        // 大文本类型
        else if (type.startsWith("CLOB") || type.equals("LONG")) {
            return "text";
        }
        // 二进制类型
        else if (type.equals("BLOB")) {
            return "blob";
        }
        // 布尔类型
        else if (type.equals("BOOLEAN")) {
            return "tinyint(1)";
        }
        // 未匹配到的类型默认 VARCHAR(100)
        else {
            System.err.println("未匹配的类型：" + type);
            return "varchar(100)";
        }
    }

    /**
     * 使用Oracle或MySql字段生成MySql建表语句
     *
     * @param tabXmlDos  生成所需表字段参数
     * @param sqlName    生成的sql文件名称
     * @param sourceDb   源数据库类型
     * @param addDropSql 是否添加删表语句
     * @return MySql 建表 SQL
     */
    public static void tranTabToMySql(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql) {
        // 默认值处理
        String actualEngine = "InnoDB";
        StringBuilder result = new StringBuilder();
        for (TabXmlDo tabXmlDo : tabXmlDos) {
            // 表名转小写
            String tableName = tabXmlDo.tableName().tableName().trim().toLowerCase();
            // 生成字段定义部分
            String columnsSql = tabXmlDo.tableDDLList().stream()
                    .map(i -> GenSqlScr.buildColToMysql(i, sourceDb))
                    .collect(Collectors.joining(",\n  "));

            // 生成主键约束部分
            String primaryKeySql = buildPrimaryKeySql(tabXmlDo.tableDDLList());
            // 1. 生成删表语句（存在即删，避免重复创建报错）
            String dropTableSql = addDropSql ? String.format("drop table if exists %s;\n", tableName) : "";
            // 拼接完整建表语句
            result.append(String.format("\n%screate table %s (\n  %s%s\n) comment='%s' engine=%s;\n",
                    dropTableSql,
                    tableName,
                    columnsSql,
                    primaryKeySql.isEmpty() ? "" : ",\n  " + primaryKeySql,
                    tabXmlDo.tableName().comments(),
                    actualEngine
            ));
        }
        StrToFile.toFile(result.toString(), sqlName);
    }

    /**
     * MySql版：构建单个字段的SQL片段
     */
    private static String buildColToMysql(TableDDL ddl, DbType sourceDb) {
        // 字段名转小写
        String columnName = ddl.columnName().trim().toLowerCase();
        // 字段类型（Oracle → MySql 映射）
        String mysqlType;
        if (sourceDb == DbType.MySql) {
            // 本来是MySql就无需转换
            mysqlType = ddl.dataType().trim().toLowerCase();
        } else {
            // Oracle需转为MySql类型
            mysqlType = mapOracleTypeToMysql(ddl.dataType());
        }
        // 非空约束
        String notNull = "Y".equalsIgnoreCase(ddl.isNotNull()) ? " not null" : "";
        // 字段注释（为空则不添加）
        String comment = ddl.comments() != null && !ddl.comments().isBlank()
                ? " comment '" + ddl.comments().trim() + "'"
                : "";
        // 默认值
        String defaultValue = buildDefaultValMySql(ddl.defaultVal(), mysqlType);
        return columnName + " " + mysqlType + defaultValue + notNull + comment;
    }

    /**
     * MySql版：构建主键约束SQL片段
     */
    private static String buildPrimaryKeySql(List<TableDDL> ddlList) {
        // 筛选所有主键字段
        List<String> primaryColumns = ddlList.stream()
                .filter(ddl -> "Y".equalsIgnoreCase(ddl.isPri()))
                .map(ddl -> ddl.columnName().trim().toLowerCase())
                .collect(Collectors.toList());

        if (primaryColumns.isEmpty()) {
            return ""; // 无主键时返回空
        }
        return "primary key (" + String.join(", ", primaryColumns) + ")";
    }

    /**
     * MySql版：解析默认值，生成DEFAULT语法片段
     */
    private static String buildDefaultValMySql(String defaultValue, String mysqlType) {
        // 1. 无默认值（null/空字符串），直接返回空
        if (defaultValue == null || defaultValue.isBlank()) {
            return "";
        }
        String trimmedDefault = defaultValue.trim();
        // 2. 特殊默认值（无需加单引号，如函数、数值、布尔值）
        // 匹配规则：数值、CURRENT_TIMESTAMP、NOW()、NULL 等
        boolean isSpecialDefault =
                trimmedDefault.matches("^\\d+$") // 纯数字（如 0、100）
                        || trimmedDefault.matches("^\\d+\\.\\d+$") // 小数（如 10.20）
                        || trimmedDefault.equalsIgnoreCase("CURRENT_TIMESTAMP")
                        || trimmedDefault.equalsIgnoreCase("NOW()")
                        || trimmedDefault.equalsIgnoreCase("NULL")
                        || trimmedDefault.equalsIgnoreCase("TRUE")
                        || trimmedDefault.equalsIgnoreCase("FALSE");
        if (isSpecialDefault) {
            return " default " + trimmedDefault;
        }
        // 3. 字符串类型默认值（需加单引号，同时转义内部单引号）
        // 匹配字符类型：VARCHAR、CHAR、TEXT 等
        boolean isStringType = mysqlType.startsWith("varchar")
                || mysqlType.startsWith("char")
                || mysqlType.contains("text");

        // 4. 日期时间类型默认值（若输入是字符串格式，需加单引号，如 '2025-01-01'）
        boolean isDateTimeType = mysqlType.equals("datetime");
        if (isDateTimeType && "SYSDATE".equals(trimmedDefault)) {
            return " default current_timestamp";
        }
        if (isStringType || isDateTimeType) {
            // 转义单引号（如 "O'Neil" → "O''Neil"）
            String escapedDefault = trimmedDefault.replace("'", "''");
            return " default '" + escapedDefault + "'";
        }
        // 5. 其他类型（如 TINYINT、DECIMAL 等），默认加单引号（兜底处理）
        return " default '" + trimmedDefault.replace("'", "''") + "'";
    }

    // MySql → Oracle 字段类型映射表
    private static String mapMysqlTypeToOracle(String mysqlDataType) {
        String type = mysqlDataType.trim().toUpperCase();
        // 字符串类型
        if (type.startsWith("VARCHAR") || type.startsWith("CHAR")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "VARCHAR2(" + fieldLen + ")";
            }
            return "VARCHAR2(50)"; // 无长度时默认50
        }
        // 整数类型
        else if (type.startsWith("INT")) {
            return "NUMBER(10)";
        } else if (type.startsWith("BIGINT")) {
            return "NUMBER(19)";
        }
        // 小数类型
        else if (type.startsWith("DECIMAL") || type.startsWith("DOUBLE") || type.startsWith("NUMERIC")) {
            return "NUMBER" + type.substring(type.indexOf("("));
        }
        // 日期时间类型
        else if (type.equals("TIMESTAMP")) {
            return "TIMESTAMP";
        } else if (type.equals("DATETIME") || type.equals("DATE")) {
            return "DATE";
        }
        // 大文本类型
        else if (type.equals("TEXT") || type.equals("LONGTEXT")) {
            return "CLOB";
        }
        // 二进制类型
        else if (type.equals("BLOB")) {
            return "BLOB";
        }
        // 布尔类型
        else if (type.startsWith("TINYINT(1)")) {
            return "BOOLEAN";
        }
        // 未匹配到的类型默认VARCHAR2(100)
        else {
            System.err.println("未匹配的类型：" + type);
            return "VARCHAR2(100)";
        }
    }

    /**
     * 使用MySql或Oracle字段生成Oracle建表语句
     *
     * @param tabXmlDos  生成所需表字段参数
     * @param sqlName    生成的sql文件名称
     * @param sourceDb   源数据库类型
     * @param addDropSql 是否添加删表语句
     * @return Oracle 建表 SQL
     */
    public static void tranTabToOracle(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql) {
        StringBuilder result = new StringBuilder();
        for (TabXmlDo tabXmlDo : tabXmlDos) {
            // 表名保持大写（Oracle习惯）
            String tableName = tabXmlDo.tableName().tableName().trim().toUpperCase();
            // 生成字段定义部分
            String columnsSql = tabXmlDo.tableDDLList().stream()
                    .map(i -> GenSqlScr.buildColToOracle(i, sourceDb))
                    .collect(Collectors.joining(",\n  "));
            // 生成主键约束部分
            String primaryKeySql = buildPrimaryKeySqlOracle(tabXmlDo.tableDDLList());
            // 生成删表语句（存在即删）
            String dropTableSql = addDropSql ? String.format("DROP TABLE %s PURGE;\n", tableName) : "";
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
     * Oracle版：构建单个字段的SQL片段
     */
    private static String buildColToOracle(TableDDL ddl, DbType sourceDb) {
        // 字段名保持大写（Oracle习惯）
        String columnName = ddl.columnName().trim().toUpperCase();
        // 字段类型（MySql → Oracle 映射）
        String oracleType;
        if (sourceDb == DbType.Oracle) {
            // 本来是Oracle就无需转换
            oracleType = ddl.dataType().trim().toUpperCase();
        } else {
            // MySql需转为Oracle类型
            oracleType = mapMysqlTypeToOracle(ddl.dataType());
        }
        // 非空约束
        String notNull = "Y".equalsIgnoreCase(ddl.isNotNull()) ? " NOT NULL" : "";
        // 默认值
        String defaultValue = buildDefaultValOracle(ddl.defaultVal(), oracleType);
        return columnName + " " + oracleType + defaultValue + notNull;
    }

    /**
     * Oracle版：构建主键约束SQL片段
     */
    private static String buildPrimaryKeySqlOracle(List<TableDDL> ddlList) {
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
     * Oracle版：解析默认值，生成DEFAULT语法片段
     */
    private static String buildDefaultValOracle(String defaultValue, String oracleType) {
        if (defaultValue == null || defaultValue.isBlank()) {
            return "";
        }
        String trimmedDefault = defaultValue.trim();
        // 特殊默认值（函数、数值、布尔值）
        boolean isSpecialDefault =
                trimmedDefault.matches("^\\d+$") // 纯数字
                        || trimmedDefault.matches("^\\d+\\.\\d+$") // 小数
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
        if (isDateTimeType && "CURRENT_TIMESTAMP".equalsIgnoreCase(trimmedDefault)) {
            return " DEFAULT SYSDATE";
        }
        if (isStringType || isDateTimeType) {
            // 转义单引号
            String escapedDefault = trimmedDefault.replace("'", "''");
            return " DEFAULT '" + escapedDefault + "'";
        }
        // 其他类型兜底处理
        return " DEFAULT '" + trimmedDefault.replace("'", "''") + "'";
    }
}