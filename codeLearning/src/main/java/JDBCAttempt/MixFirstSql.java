package JDBCAttempt;

import java.sql.*;

public class MixFirstSql {
    public static void main(String[] args) {
        try {
            //建立连接
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载JDBC_MySQL驱动，原理是加载里面的静态代码块，所以是这种形式
            String uri = "jdbc:mysql://localhost:3306/shixun0515?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(uri, user, password); //连接代码
            // 开始查询
            Statement sql = con.createStatement();
            ResultSet rs = sql.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int sex = rs.getInt(3);
                double height = rs.getDouble("height");
                System.out.printf("%s\t %s\t %s\t %s\n", id, name, sex, height);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("SQL异常"+e);
        } catch (ClassNotFoundException e2) {
            System.out.println("驱动加载失败");
        }
    }
}
