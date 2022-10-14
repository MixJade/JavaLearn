package JDBCAttempt;

import java.sql.*;

public class ReallySql {
    public static void main(String[] args) {
        ReallySql reallySql = new ReallySql();
        reallySql.verifyLogin("pixiv", "pixiv0223");
    }

    public void verifyLogin(String loginName, String loginPassword) {
        try {
            // useServerPrepStmts=ture  ：开启预编译，可以重复套sql模板，降低时间耗费
            String url = "jdbc:mysql:///shixun0515?useSSL=true&serverTimezone=UTC&useServerPrepStmts=true";
            String user = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(url, user, password);
            // 开始查询
            String sql = "SELECT idJade FROM loginmixjade WHERE nameJade = ? AND passwordJade=?";
            // PreparedStatement预编译，防止sql注入
            PreparedStatement preState=con.prepareStatement(sql);
            preState.setString(1,loginName);
            preState.setString(2,loginPassword);
            // 获取结果不再需要传递sql语句，因为早就弄了，相应的，不能一个对象执行多个sql语句了。
            // 如果仅仅只是换一个值查询的话，可以再次通过setString来重新设值
            ResultSet rs02 = preState.executeQuery();
            if (rs02.next()) {
                System.out.println("登录成功，id是：" + rs02.getString(1));
            } else System.out.println("登录失败，请检查密码和用户名是否正确");
            preState.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQL异常:\n" + e);
        }
    }
}
