package JDBCAttempt;

import java.sql.*;

public class MixSecondSql {
    public static void main(String[] args) {
        try {
/*
             这个驱动其实可以不用加载，因为现在的版本已经优化了：
            Class.forName("com.mysql.cj.jdbc.Driver");
             建立连接，用127.0.0.1（就是localhost的另一种写法）：
             String url = "jdbc:mysql://127.0.0.1:3306/shixun0515?useSSL=true&serverTimezone=CST";
             其实，如果只是连接本地数据库，并且端口号是3306（mysql默认端口号）且mysql版本大于5，可以直接把端口号省略：
             改成如下："jdbc:mysql:///play?useSSL=true&serverTimezone=UTC"
*/
            String url = DatabaseConfig.SIMPLE_URL;
            String user = DatabaseConfig.USERNAME;
            String password = DatabaseConfig.PASSWORD;
            Connection con = DriverManager.getConnection(url, user, password); //连接代码
            // 开始查询
            Statement sql = con.createStatement();
            int rs01 = sql.executeUpdate("UPDATE students SET birthday = '2021-02-01',mathGrade='100' WHERE studentName='张三'");
            //注：executeUpdate返回的是受影响行数
            if (rs01 > 0) System.out.println("修改成功，成功修改了" + rs01 + "行数据");
            else System.out.println("修改失败");
            ResultSet rs02 = sql.executeQuery("SELECT studentName,mathGrade,birthday FROM students WHERE studentName='张三'");
            while (rs02.next()) {
                String rs021 = rs02.getString(1), rs022 = rs02.getString(2), rs023 = rs02.getString(3);
                System.out.println("姓名：" + rs021 + " 数学成绩：" + rs022 + " 出生日期：" + rs023);
            }
            sql.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQL异常:  " + e);
        }
    }
}
