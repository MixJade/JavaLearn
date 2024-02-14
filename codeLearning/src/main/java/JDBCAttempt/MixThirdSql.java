package JDBCAttempt;

import java.sql.*;

/**
 * 关于事务的JDBC
 */
public class MixThirdSql {
    public static void main(String[] args) {
        affairAttempt(200);
    }

    public static void affairAttempt(int transferMoney) {
        try {
            String url = DatabaseConfig.SIMPLE_URL;
            String user = DatabaseConfig.USERNAME;
            String password = DatabaseConfig.PASSWORD;
            Connection con = DriverManager.getConnection(url, user, password); //连接代码
            Statement state = con.createStatement();
            try {
                con.setAutoCommit(false);//设置手动提交
                int sqlRs01 = state.executeUpdate("UPDATE societys SET account = account -" + transferMoney + "  WHERE societyName='黄金之风'");
                int sqlRs02 = state.executeUpdate("UPDATE societys SET account = account + " + transferMoney + " WHERE societyName='轻音部'");
                if (sqlRs01 == 1 && sqlRs02 == 1) System.out.println("事务成功，转账了" + transferMoney + "元");
                // 提交事务，这个不能忘记
                con.commit();
            } catch (Exception e) {
                con.rollback();
                System.out.println("事务已回滚");
            } finally {
                ResultSet sqlRs03 = state.executeQuery("SELECT societyName,account FROM societys WHERE societyName='黄金之风' OR societyName='轻音部'");
                while (sqlRs03.next()) {
                    System.out.println("名称： " + sqlRs03.getString(1) + " 经费：" + sqlRs03.getString(2));
                }
                state.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("SQL异常:  " + e);
        }
    }
}
