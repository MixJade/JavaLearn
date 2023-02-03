package snakeGame.handle;

import snakeGame.model.Login;

import java.sql.*;
import javax.swing.JOptionPane;

public class HandleLogin {
    Connection con;
    PreparedStatement preSql;
    ResultSet rs;

    public HandleLogin() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String uri = "jdbc:mysql://localhost:3306/shixun0515?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "root";
            con = DriverManager.getConnection(uri, user, password);
        } catch (SQLException e) {
            System.out.println("连接失败" + e);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动失败" + e);
        }
    }

    public Login queryVerify(Login loginModel) {
        String nameJade = loginModel.getNameJade();
        String passwordJade = loginModel.getPasswordJade();
        String sqlStr = "select nameJade,passwordJade from loginmixjade where nameJade = ? and passwordJade = ?";
        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, nameJade);
            preSql.setString(2, passwordJade);
            rs = preSql.executeQuery();
            if (rs.next()) {
                loginModel.setLoginSuccess(true);
                JOptionPane.showMessageDialog(null, "登录成功",
                        "恭喜", JOptionPane.INFORMATION_MESSAGE);
            } else {
                loginModel.setLoginSuccess(false);
                JOptionPane.showMessageDialog(null, "登录失败",
                        "登录失败，重新登录", JOptionPane.ERROR_MESSAGE);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("查询失败" + e);
        }
        return loginModel;
    }
}
