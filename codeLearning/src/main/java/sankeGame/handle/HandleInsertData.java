package sankeGame.handle;


import sankeGame.model.Register;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleInsertData {
    Connection con;
    PreparedStatement preSql;

    public HandleInsertData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String uri = "jdbc:mysql://localhost:3306/shixun0515?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "root";
            con = DriverManager.getConnection(uri, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "注册失败", "散玉说", JOptionPane.WARNING_MESSAGE);
            System.out.println("连接失败" + e);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "mysql依赖包加载失败", "散玉说", JOptionPane.QUESTION_MESSAGE);
            System.out.println("驱动失败" + e);
        }
    }

    public void writeRegisterModel(Register register) {
        String sqlStr = "insert into loginmixjade values(?,?)";
        int ok = 0;
        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, register.getNameJade());
            preSql.setString(2, register.getPasswordJade());
            ok = preSql.executeUpdate();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "没有在数据库找到表", "散玉说", JOptionPane.ERROR_MESSAGE);
        }
        if (ok != 0) {
            JOptionPane.showMessageDialog(null, "注册成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
