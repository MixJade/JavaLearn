package work.utils;

import work.model.dto.TabXmlDo;
import work.model.entity.TableDDL;
import work.model.entity.TableName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 建表语句生成工具
 *
 * @since 2025-11-26 09:13:12
 */
public class GenSqlScr {

    // Oracle → MySQL 字段类型映射表（可根据实际需求扩展）
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
        else if (type.equals("CLOB") || type.equals("LONG")) {
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
            return "varchar(100)";
        }
    }

    /**
     * 使用Oracle字段生成MySQL建表语句
     *
     * @param tabXmlDos    生成所需表字段参数
     * @param sqlName      生成的sql文件名称
     * @param addDropTable 是否添加删表语句
     * @return MySQL 建表 SQL
     */
    public static void tranOracleToMySql(List<TabXmlDo> tabXmlDos, String sqlName, boolean addDropTable) {
        // 默认值处理
        String actualEngine = "InnoDB";
        StringBuilder result = new StringBuilder();
        for (TabXmlDo tabXmlDo : tabXmlDos) {
            // 表名转小写
            String tableName = tabXmlDo.tableName().tableName().trim().toLowerCase();

            // 生成字段定义部分
            String columnsSql = tabXmlDo.tableDDLList().stream()
                    .map(GenSqlScr::buildColOracleToMysql)
                    .collect(Collectors.joining(",\n  "));

            // 生成主键约束部分
            String primaryKeySql = buildPrimaryKeySql(tabXmlDo.tableDDLList());

            // 1. 生成删表语句（存在即删，避免重复创建报错）
            String dropTableSql = addDropTable ? String.format("drop table if exists %s;\n", tableName) : "";

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
     * 构建单个字段的 SQL 片段
     */
    private static String buildColOracleToMysql(TableDDL ddl) {
        // 字段名转小写
        String columnName = ddl.columnName().trim().toLowerCase();
        // 字段类型（Oracle → MySQL 映射）
        String mysqlType = mapOracleTypeToMysql(ddl.dataType());
        // 非空约束
        String notNull = "Y".equalsIgnoreCase(ddl.isNotNull()) ? " not null" : "";
        // 字段注释（为空则不添加）
        String comment = ddl.comments() != null && !ddl.comments().isBlank()
                ? " comment '" + ddl.comments().trim() + "'"
                : "";
        // 默认值
        String defaultValue = buildDefaultValueSql(ddl.defaultVal(), mysqlType);
        return columnName + " " + mysqlType + defaultValue + notNull + comment;
    }

    /**
     * MySql版：构建主键约束 SQL 片段
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
    private static String buildDefaultValueSql(String defaultValue, String mysqlType) {
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


    // ------------------------------ 测试用例 ------------------------------
    public static void main(String[] args) {
        // 构建测试数据
        List<TableDDL> ddlList = new ArrayList<>();
        ddlList.add(new TableDDL("1", "ID", "NUMBER(19)", "主键ID", "Y", "Y", null));
        ddlList.add(new TableDDL("2", "USER_NAME", "VARCHAR2(50)", "用户姓名", "N", "Y", null));
        ddlList.add(new TableDDL("3", "AGE", "NUMBER(3)", "年龄", "N", "N", "12"));
        ddlList.add(new TableDDL("4", "BALANCE", "NUMBER(10,2)", "账户余额", "N", "N", null));
        ddlList.add(new TableDDL("5", "CREATE_TIME", "DATE", "创建时间", "N", "Y", "SYSDATE"));
        ddlList.add(new TableDDL("6", "REMARK", "CLOB", "备注信息", "N", "N", null));

        TabXmlDo tabXmlDo = new TabXmlDo(new TableName("T_USER_INFO", "表注释"), ddlList);
        // 生成建表语句
        List<TabXmlDo> tabXmlDos = new ArrayList<>();
        tabXmlDos.add(tabXmlDo);
        tabXmlDos.add(tabXmlDo);
        tranOracleToMySql(tabXmlDos, "测试建表语句.sql", false);
    }
}