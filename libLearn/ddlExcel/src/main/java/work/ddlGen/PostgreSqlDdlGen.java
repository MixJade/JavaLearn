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
 * PostgreSQL 建表语句生成器
 * <p>
 * 负责将源数据库字段转换为 PostgreSQL 建表语句。
 * 字段类型转换路径：源类型 → 中间类型(MySQL) → PostgreSQL类型。
 * <p>
 * PostgreSQL DDL 特点：
 * <ul>
 *     <li>标识符使用小写（PostgreSQL 默认将无引号标识符折叠为小写）</li>
 *     <li>注释使用 COMMENT ON TABLE/COLUMN 独立语句（与 Oracle 类似）</li>
 *     <li>删表使用 DROP TABLE IF EXISTS</li>
 *     <li>不指定 engine</li>
 *     <li>原生支持 BOOLEAN 类型</li>
 * </ul>
 *
 * @since 2026-07-17
 */
public class PostgreSqlDdlGen implements DdlGen {

    @Override
    public void generate(List<TabXmlDo> tabXmlDos, String sqlName, DbType sourceDb, boolean addDropSql) {
        DdlGen sourceConverter = DdlGenFactory.get(sourceDb);
        StringBuilder result = new StringBuilder();

        for (TabXmlDo tabXmlDo : tabXmlDos) {
            // 表名保持小写（PostgreSQL习惯）
            String tableName = tabXmlDo.tableName().tableName().trim().toLowerCase();
            // 生成字段定义部分
            String columnsSql = tabXmlDo.tableDDLList().stream()
                    .map(ddl -> buildColumn(ddl, sourceDb, sourceConverter))
                    .collect(Collectors.joining(",\n  "));
            // 生成主键约束部分
            String primaryKeySql = buildPrimaryKey(tabXmlDo.tableDDLList());
            // 删表语句（存在即删）
            String dropTableSql = addDropSql ? String.format("DROP TABLE IF EXISTS %s;\n", tableName) : "";
            // 拼接完整建表语句（PostgreSQL不指定engine）
            result.append(String.format("\n%sCREATE TABLE %s (\n  %s%s\n);\n",
                    dropTableSql,
                    tableName,
                    columnsSql,
                    primaryKeySql.isEmpty() ? "" : ",\n  " + primaryKeySql
            ));
            // 添加表注释（PostgreSQL使用COMMENT ON语句，与Oracle类似）
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
                            ddl.columnName().trim().toLowerCase(),
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
    private String buildColumn(TableDDL ddl, DbType sourceDb, DdlGen sourceConverter) {
        // 字段名保持小写（PostgreSQL习惯）
        String columnName = ddl.columnName().trim().toLowerCase();
        // 字段类型转换
        String pgType;
        if (sourceDb == DbType.PostgreSql) {
            // 同库无需转换，直接使用原生类型
            pgType = ddl.dataType().trim().toLowerCase();
        } else {
            // 跨库转换：源类型 → 中间类型(MySQL) → PostgreSQL类型
            String middleType = sourceConverter.toMiddleType(ddl.dataType());
            pgType = this.fromMiddleType(middleType).trim().toLowerCase();
        }
        // 非空约束
        String notNull = "Y".equalsIgnoreCase(ddl.isNotNull()) ? " NOT NULL" : "";
        // 默认值
        String defaultValue = buildDefaultVal(ddl.defaultVal(), pgType);
        return columnName + " " + pgType + defaultValue + notNull;
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
        return "PRIMARY KEY (" + String.join(", ", primaryColumns) + ")";
    }

    /**
     * 解析默认值，生成DEFAULT语法片段
     */
    private String buildDefaultVal(String defaultValue, String pgType) {
        if (defaultValue == null || defaultValue.isBlank()) {
            return "";
        }
        String trimmedDefault = defaultValue.trim();
        // 特殊默认值（函数、数值、布尔值）—— 无需加单引号
        boolean isSpecialDefault =
                trimmedDefault.matches("^\\d+$")                              // 纯数字
                        || trimmedDefault.matches("^-?\\d+\\.\\d+$")           // 小数（含负数）
                        || trimmedDefault.equalsIgnoreCase("CURRENT_TIMESTAMP")
                        || trimmedDefault.equalsIgnoreCase("NOW()")
                        || trimmedDefault.equalsIgnoreCase("NULL")
                        || trimmedDefault.equalsIgnoreCase("TRUE")
                        || trimmedDefault.equalsIgnoreCase("FALSE")
                        || trimmedDefault.equalsIgnoreCase("DEFAULT");
        if (isSpecialDefault) {
            return " DEFAULT " + trimmedDefault;
        }
        // Oracle的SYSDATE → PostgreSQL的CURRENT_TIMESTAMP
        if ("SYSDATE".equalsIgnoreCase(trimmedDefault)) {
            return " DEFAULT CURRENT_TIMESTAMP";
        }
        // MySQL的NOW() → PostgreSQL的NOW()（PostgreSQL也支持NOW()）
        // 字符串类型默认值（需加单引号）
        boolean isStringType = pgType.startsWith("varchar")
                || pgType.startsWith("char")
                || pgType.contains("text");
        // 日期时间类型默认值
        boolean isDateTimeType = pgType.equals("timestamp")
                || pgType.equals("timestamptz")
                || pgType.equals("date")
                || pgType.equals("time");
        if (isStringType || isDateTimeType) {
            String escapedDefault = trimmedDefault.replace("'", "''");
            return " DEFAULT '" + escapedDefault + "'";
        }
        // 其他类型兜底处理
        return " DEFAULT '" + trimmedDefault.replace("'", "''") + "'";
    }

    // ==================== PostgreSQL → MySQL（toMiddleType）====================

    /**
     * PostgreSQL → MySQL 字段类型映射
     */
    @Override
    public String toMiddleType(String nativeType) {
        String type = nativeType.trim().toLowerCase();

        // 字符串类型：varchar / character varying → varchar(len)
        if (type.startsWith("varchar") || type.startsWith("character varying")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "varchar(" + fieldLen + ")";
            }
            return "varchar(50)";
        }
        // 定长字符串：char / character → char(len)
        if (type.startsWith("char") && !type.startsWith("character varying")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "char(" + fieldLen + ")";
            }
            return "char";
        }
        // 大文本类型：text → text
        if (type.equals("text")) {
            return "text";
        }
        // 整数类型：integer / int / int4 / serial → int
        if (type.equals("integer") || type.equals("int") || type.equals("int4") || type.equals("serial")) {
            return "int";
        }
        // 大整数类型：bigint / int8 / bigserial → bigint
        if (type.equals("bigint") || type.equals("int8") || type.equals("bigserial")) {
            return "bigint";
        }
        // 小整数类型：smallint / int2 → smallint
        if (type.equals("smallint") || type.equals("int2")) {
            return "smallint";
        }
        // 高精度小数：numeric(p,s) / decimal(p,s) → decimal(p,s)
        if (type.startsWith("numeric") || type.startsWith("decimal")) {
            if (type.contains("(")) {
                return "decimal" + type.substring(type.indexOf("("));
            }
            return "decimal";
        }
        // 浮点类型：real / float4 → float
        if (type.equals("real") || type.equals("float4") || type.equals("float")) {
            return "float";
        }
        // 双精度：double precision / float8 → double
        if (type.equals("double precision") || type.equals("float8")) {
            return "double";
        }
        // 布尔类型：boolean / bool → tinyint(1)
        if (type.equals("boolean") || type.equals("bool")) {
            return "tinyint(1)";
        }
        // 日期类型：date → date
        if (type.equals("date")) {
            return "date";
        }
        // 时间戳：timestamp / timestamptz → timestamp
        if (type.startsWith("timestamp")) {
            return "timestamp";
        }
        // 时间类型：time / timetz → time
        if (type.startsWith("time") && !type.startsWith("timestamp")) {
            return "time";
        }
        // 二进制类型：bytea → blob
        if (type.equals("bytea")) {
            return "blob";
        }
        // UUID类型 → varchar(36)
        if (type.equals("uuid")) {
            return "varchar(36)";
        }
        // JSON类型 → text
        if (type.equals("json") || type.equals("jsonb")) {
            return "text";
        }
        // XML类型 → text
        if (type.equals("xml")) {
            return "text";
        }
        // 未匹配到的类型默认 varchar(100)
        System.err.println("未匹配的类型：" + type);
        return "varchar(100)";
    }

    // ==================== MySQL → PostgreSQL（fromMiddleType）====================

    /**
     * MySQL → PostgreSQL 字段类型映射
     */
    @Override
    public String fromMiddleType(String middleType) {
        String type = middleType.trim().toLowerCase();

        // 字符串类型：varchar → varchar(len)
        if (type.startsWith("varchar")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "VARCHAR(" + fieldLen + ")";
            }
            return "VARCHAR(50)";
        }
        // 定长字符串：char → char(len)
        if (type.startsWith("char")) {
            if (type.contains("(")) {
                String fieldLen = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                return "CHAR(" + fieldLen + ")";
            }
            return "CHAR";
        }
        // 大文本类型：text / longtext / mediumtext / tinytext → TEXT
        if (type.equals("text") || type.equals("longtext") || type.equals("mediumtext") || type.equals("tinytext")) {
            return "TEXT";
        }
        // 整数类型：int / integer → INTEGER
        if (type.equals("int") || type.equals("integer")) {
            return "INTEGER";
        }
        // 大整数类型：bigint → BIGINT
        if (type.startsWith("bigint")) {
            return "BIGINT";
        }
        // 小整数类型：smallint → SMALLINT
        if (type.startsWith("smallint")) {
            return "SMALLINT";
        }
        // 布尔类型：tinyint(1) → BOOLEAN
        if (type.startsWith("tinyint(1)")) {
            return "BOOLEAN";
        }
        // 微小整数：tinyint（非tinyint(1)）→ SMALLINT
        if (type.startsWith("tinyint")) {
            return "SMALLINT";
        }
        // 高精度小数：decimal / numeric → NUMERIC(p,s)
        if (type.startsWith("decimal") || type.startsWith("numeric")) {
            if (type.contains("(")) {
                return "NUMERIC" + type.substring(type.indexOf("("));
            }
            return "NUMERIC";
        }
        // 双精度：double → DOUBLE PRECISION
        if (type.startsWith("double")) {
            return "DOUBLE PRECISION";
        }
        // 浮点类型：float → REAL
        if (type.startsWith("float")) {
            return "REAL";
        }
        // 日期类型：date → DATE
        if (type.equals("date")) {
            return "DATE";
        }
        // 日期时间：datetime → TIMESTAMP
        if (type.equals("datetime")) {
            return "TIMESTAMP";
        }
        // 时间戳：timestamp → TIMESTAMP
        if (type.equals("timestamp")) {
            return "TIMESTAMP";
        }
        // 时间类型：time → TIME
        if (type.equals("time")) {
            return "TIME";
        }
        // 二进制类型：blob / longblob / mediumblob / tinyblob → BYTEA
        if (type.equals("blob") || type.equals("longblob") || type.equals("mediumblob") || type.equals("tinyblob")) {
            return "BYTEA";
        }
        // 布尔类型：boolean → BOOLEAN
        if (type.equals("boolean") || type.equals("bool")) {
            return "BOOLEAN";
        }
        // 未匹配到的类型默认 VARCHAR(100)
        System.err.println("未匹配的类型：" + type);
        return "VARCHAR(100)";
    }

    // ==================== PostgreSQL → Java（toJavaType）====================

    /**
     * PostgreSQL字段类型 → Java类型
     */
    @Override
    public JType toJavaType(OriTabCol oriTabCol) {
        String pureType = oriTabCol.dataType().toUpperCase().trim();
        return switch (pureType) {
            // 整数类型
            case "INTEGER", "INT", "INT4", "SERIAL" -> JType.INT;
            case "BIGINT", "INT8", "BIGSERIAL" -> JType.LONG;
            case "SMALLINT", "INT2" -> JType.INT;
            // 布尔类型
            case "BOOLEAN", "BOOL" -> JType.BOOL;
            // 浮点/高精度类型
            case "REAL", "FLOAT4", "FLOAT" -> JType.DOUBLE;
            case "DOUBLE PRECISION", "FLOAT8" -> JType.DOUBLE;
            case "NUMERIC", "DECIMAL" -> JType.DECIMAL;
            // 字符串类型
            case "VARCHAR", "CHARACTER VARYING", "CHAR", "CHARACTER", "TEXT" -> JType.STR;
            // 日期时间类型
            case "DATE" -> JType.DATE;
            case "TIME", "TIMETZ" -> JType.TIME;
            case "TIMESTAMP", "TIMESTAMPTZ" -> JType.DATE_TIME;
            // 二进制类型
            case "BYTEA" -> JType.BYTE;
            // 其他类型（UUID、JSON、JSONB、XML）→ String
            case "UUID", "JSON", "JSONB", "XML" -> JType.STR;
            // 兜底：未匹配的类型统一返回String
            default -> JType.STR;
        };
    }

    @Override
    public boolean insertBatch() {
        return true;
    }
}
