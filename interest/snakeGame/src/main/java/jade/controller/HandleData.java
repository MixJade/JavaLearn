package jade.controller;

import javax.swing.*;
import java.sql.*;

public class HandleData {
    String sqlQuery = "SELECT COUNT(*) FROM loginmixjade WHERE nameJade=? AND passwordJade=? ";
    public void writeRegisterModel(String name, String pwd) {
        try (Connection con = getCon()) {
            PreparedStatement preSql01 = con.prepareStatement(sqlQuery);
            preSql01.setString(1, name);
            preSql01.setString(2, pwd);
            ResultSet nowSum = preSql01.executeQuery();
            if (nowSum.next() && nowSum.getInt(1) > 0) {
                String msg = String.format("已有账号，账号:%s,密码:%s", name, pwd);
                JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String sqlInsert = "INSERT INTO loginmixjade(nameJade,passwordJade) VALUES(?,?) ";
                PreparedStatement preSql02 = con.prepareStatement(sqlInsert);
                preSql02.setString(1, name);
                preSql02.setString(2, pwd);
                int ok = preSql02.executeUpdate();
                if (ok != 0) {
                    JOptionPane.showMessageDialog(null, "注册成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "没有在数据库找到表", "散玉说", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean queryVerify(String name, String pwd) {
        boolean isSuc = false;
        try (Connection con = getCon()) {
            PreparedStatement preSql = con.prepareStatement(sqlQuery);
            preSql.setString(1, name);
            preSql.setString(2, pwd);
            ResultSet rs = preSql.executeQuery();
            if (rs.next() && (rs.getInt(1) > 0)) {
                isSuc = true;
                JOptionPane.showMessageDialog(null, "登录成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "账号密码输入错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("查询失败" + e);
        }
        return isSuc;
    }

    public Connection getCon() {
        try {
            // 2024年1月19日更新，新增配置接口文件来设置数据库链接
            Class.forName("com.mysql.cj.jdbc.Driver");
            String uri = SnakeDataConfig.URL;
            String user = SnakeDataConfig.USERNAME;
            String password = SnakeDataConfig.PASSWORD;
            return DriverManager.getConnection(uri, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "注册失败", "散玉说", JOptionPane.WARNING_MESSAGE);
            System.out.println("连接失败" + e);
            return null;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "mysql依赖包加载失败", "散玉说", JOptionPane.QUESTION_MESSAGE);
            System.out.println("驱动失败" + e);
            return null;
        }
    }
}
