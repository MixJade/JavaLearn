package JDBCAttempt;

import java.sql.*;
import java.util.ArrayList;

public class MixFourthSql {
    public static void main(String[] args) {
        // 在循环查询之前建立一个集合，将查询的数据放入集合中
        ArrayList<StudentsMessage> smList = new ArrayList<>();
        try {
            //建立连接
            Class.forName("com.mysql.cj.jdbc.Driver"); //加载JDBC_MySQL驱动，原理是加载里面的静态代码块，所以是这种形式
            String uri = "jdbc:mysql://localhost:3306/shixun0515?useSSL=true&serverTimezone=UTC";
            String user = "root";
            String password = "root";
            Connection con = DriverManager.getConnection(uri, user, password); //连接代码
            // 开始查询
            Statement sql = con.createStatement();
            ResultSet rs = sql.executeQuery("SELECT id,studentName,sex,height FROM students");
            while (rs.next()) {
                int id = rs.getInt(1);
                // getXXX，参数可以是数字（获取索引位置的列）或者字符串（获取指定名字的列）
                String name = rs.getString("studentName");
                int sex = rs.getInt("sex");
                double height = rs.getDouble(4);
                StudentsMessage sm = new StudentsMessage();
                sm.setMessage(id, name, sex, height);
                smList.add(sm);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("SQL异常: " + e);
        } catch (ClassNotFoundException e2) {
            System.out.println("驱动加载失败");
        }
        for (StudentsMessage studentsMessage : smList) {
            System.out.println("    "+studentsMessage.studentName);
            studentsMessage.getStudentsMessage();
        }
    }
}

class StudentsMessage {
    int id;
    boolean sex;
    String studentName;
    double height;

    public void setMessage(int id, String name, int sex, double height) {
        this.id = id;
        this.studentName = name;
        this.height = height;
        this.sex = sex == 1;
    }

    public void getStudentsMessage() {
        System.out.println("id:" + id + " 姓名：" + studentName + " 性别：" + (sex ? "男" : "女") + " 身高：" + height);
    }
}
