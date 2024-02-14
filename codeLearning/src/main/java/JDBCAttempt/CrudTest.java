package JDBCAttempt;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudTest {
    @Test
    public void demandAttempt() {
        demandDemo("布加拉提");
        demandDemo(new String[]{"平泽唯", "田井中律", "张三", "米斯达", "小鸟游六花"});
    }

    @Test
    public void increaseAttempt() {
        increaseDemo("王老五", 1.88);
        demandDemo("王老五");
    }


    @Test
    public void alterAttempt() {
        alterDemo("王老五", 100, 100);
        demandDemo("王老五");
    }

    @Test
    public void omitAttempt() {
        omitDemo("王老五");
    }

    public void demandDemo(String studentName) {
        demandDemo(new String[]{studentName});
    }

    private void demandDemo(String[] studentsName) {
        System.out.println("    开始查询：");
        try {
            DataSource dataSource = CrudUtil.druidGet();
            Connection con = dataSource.getConnection();
            String sql = "SELECT * FROM students s1,societys s2 WHERE s1.studentName=? AND s2.societyId=s1.societyId";
            PreparedStatement preState = con.prepareStatement(sql);
            for (String studentName01 : studentsName) {
                preState.setString(1, studentName01);
                ResultSet rs01 = preState.executeQuery();
                // 出结果
                while (rs01.next()) {
                    int id = rs01.getInt("id");
                    String studentName = rs01.getString("studentName");
                    int englishGrade = rs01.getInt("englishGrade");
                    int mathGrade = rs01.getInt("mathGrade");
                    int money = rs01.getInt("money");
                    String societysName = rs01.getString("societyName");
                    System.out.println(id + " " + studentName + " 英语：" + englishGrade + " 数学：" + mathGrade + " 零花钱：" + money + " 部门：" + societysName);
                }
            }
            con.close();
            preState.close();
        } catch (SQLException e) {
            System.out.println("    发生异常:\n" + e);
        }
    }

    public void increaseDemo(String studentName, Double height) {
        System.out.println("    开始插入，插入对象：" + studentName);
        try {
            DataSource dataSource = CrudUtil.druidGet();
            Connection con = dataSource.getConnection();
            String sql = "INSERT INTO students(studentName,height) VALUES (?,?)";
            PreparedStatement preState = con.prepareStatement(sql);
            preState.setString(1, studentName);
            preState.setDouble(2, height);
            int rs02 = preState.executeUpdate();
            if (rs02 > 0) System.out.println("插入成功，插入了：" + rs02 + "行");
            else System.out.println("插入失败");
            con.close();
            preState.close();
        } catch (SQLException e) {
            System.out.println("    发生异常:\n" + e);
        }
    }

    public void alterDemo(String studentName, int englishGrade, int mathGrade) {
        System.out.println("    开始修改，修改对象：" + studentName);
        try {
            DataSource dataSource = CrudUtil.druidGet();
            Connection con = dataSource.getConnection();
            String sql = "UPDATE students SET englishGrade=?,mathGrade=? WHERE studentName=?";
            PreparedStatement preState = con.prepareStatement(sql);
            preState.setInt(1, englishGrade);
            preState.setInt(2, mathGrade);
            preState.setString(3, studentName);
            int rs02 = preState.executeUpdate();
            if (rs02 > 0) System.out.println("修改成功，修改了：" + rs02 + "行");
            else System.out.println("修改失败");
            con.close();
            preState.close();
        } catch (SQLException e) {
            System.out.println("    发生异常:\n" + e);
        }
    }

    public void omitDemo(String studentName) {
        System.out.println("    开始删除，删除对象： " + studentName);
        try {
            DataSource dataSource = CrudUtil.druidGet();
            Connection con = dataSource.getConnection();
            String sql = "DELETE FROM students WHERE studentName = ?";
            PreparedStatement preState = con.prepareStatement(sql);
            preState.setString(1, studentName);
            int rs02 = preState.executeUpdate();
            if (rs02 > 0) System.out.println("删除成功，删除了：" + rs02 + "行");
            else System.out.println("删除失败，没有王老五");
            con.close();
            preState.close();
        } catch (SQLException e) {
            System.out.println("    发生异常:\n" + e);
        }
    }

}
