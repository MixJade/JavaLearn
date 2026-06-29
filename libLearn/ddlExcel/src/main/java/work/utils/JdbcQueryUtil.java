package work.utils;

import work.model.entity.TableRowData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Clob;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC 原生查询工具
 * <p>利用 {@link ResultSetMetaData} 直接获取列名、数据库原生类型和值，
 * 无需经过 MyBatis Map 中间层，组装为 {@link TableRowData} 列表。</p>
 */
public final class JdbcQueryUtil {

    /**
     * 查询指定表的所有数据，直接组装为结构化行列表
     *
     * @param conn      JDBC 连接（由调用方管理生命周期，此方法不关闭连接）
     * @param tableName 表名
     * @return 行列表，每行为按列顺序排列的 {@link TableRowData} 列表
     * @throws SQLException SQL 执行异常
     */
    public static List<List<TableRowData>> queryTableData(Connection conn, String tableName) throws SQLException {
        List<List<TableRowData>> result = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            // 预取列名和数据库原生类型（避免在循环体内重复调用 meta）
            String[] colNames = new String[colCount + 1];
            String[] colTypes = new String[colCount + 1];
            for (int i = 1; i <= colCount; i++) {
                colNames[i] = meta.getColumnName(i);
                // getColumnTypeName 返回数据库原生类型名，如 VARCHAR / DATE / NUMBER / TIMESTAMP
                colTypes[i] = meta.getColumnTypeName(i).toUpperCase();
            }

            // 遍历每一行数据
            while (rs.next()) {
                List<TableRowData> row = new ArrayList<>(colCount);
                for (int i = 1; i <= colCount; i++) {
                    // getObject 尽量保留 Java 原生类型（LocalDate、LocalDateTime、BigDecimal 等）
                    Object val = rs.getObject(i);
                    // 处理 CLOB 类型：转换为 String
                    val = convertClobValue(val);
                    row.add(new TableRowData(colNames[i], colTypes[i], val));
                }
                result.add(row);
            }
        }
        return result;
    }

    /**
     * 将 CLOB/BLOB 等大对象类型转换为 Java 原生类型
     *
     * @param val ResultSet 中取出的原始值
     * @return 转换后的值，CLOB -> String，BLOB -> byte[]，其他类型原样返回
     */
    private static Object convertClobValue(Object val) {
        if (val == null) {
            return null;
        }
        // 处理 CLOB 类型
        if (val instanceof Clob) {
            try {
                Clob clob = (Clob) val;
                long length = clob.length();
                if (length > 0 && length <= Integer.MAX_VALUE) {
                    return clob.getSubString(1, (int) length);
                }
            } catch (SQLException e) {
                // 转换失败，返回原始对象
                System.err.println("CLOB 转换失败: " + e.getMessage());
            }
        }
        // 处理 BLOB 类型
        if (val instanceof Blob) {
            try {
                Blob blob = (Blob) val;
                long length = blob.length();
                if (length > 0 && length <= Integer.MAX_VALUE) {
                    return blob.getBytes(1, (int) length);
                }
            } catch (SQLException e) {
                System.err.println("BLOB 转换失败: " + e.getMessage());
            }
        }
        // Oracle 专有的 CLOB/BLOB 类型处理
        String className = val.getClass().getName();
        if ("oracle.sql.CLOB".equals(className) || "oracle.jdbc.OracleClob".equals(className)) {
            try {
                // 使用反射调用 getString() 方法
                java.lang.reflect.Method method = val.getClass().getMethod("getString");
                String strVal = (String) method.invoke(val);
                if (strVal != null && !strVal.isEmpty()) {
                    return strVal;
                }
            } catch (Exception e) {
                // 反射失败，尝试其他方式
                try {
                    // 尝试获取 length 和 getSubString
                    java.lang.reflect.Method lengthMethod = val.getClass().getMethod("length");
                    long length = (Long) lengthMethod.invoke(val);
                    if (length > 0 && length <= Integer.MAX_VALUE) {
                        java.lang.reflect.Method subStrMethod = val.getClass().getMethod("getSubString", long.class, int.class);
                        return subStrMethod.invoke(val, 1L, (int) length);
                    }
                } catch (Exception ex) {
                    System.err.println("Oracle CLOB 转换失败: " + ex.getMessage());
                }
            }
        }
        return val;
    }
}
