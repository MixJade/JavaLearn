package work.utils;

import work.model.entity.TableRowData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
                    row.add(new TableRowData(colNames[i], colTypes[i], val));
                }
                result.add(row);
            }
        }
        return result;
    }
}
