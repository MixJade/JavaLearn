package JDBCAttempt;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReallyExtendSql {
    public static void main(String[] args) {
        List<StudentAndSociety> societiesList = new ArrayList<>();
        try {
            Properties prop = new Properties();
            // 注意：配置文件里面不能有双引号和分号(因为里面写中文会乱码所以写在这里）
            prop.load(new FileInputStream("src/main/resources/JDBCAttempt/druid.properties"));
            DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
            Connection con = dataSource.getConnection();
            System.out.println(con + "\n接下来的操作就完全一样了");
            //接下来的操作跟正常流程完全一样
            String sql = "SELECT s1.studentName,s1.sex,s2.societyName FROM students s1,societys s2 WHERE s1.societyId=? AND s1.sex=? AND s1.societyId=s2.societyId";
            PreparedStatement preState = con.prepareStatement(sql);
            preState.setString(1, "4");
            preState.setString(2, "1");
            ResultSet rs01 = preState.executeQuery();
            while (rs01.next()) {
                StudentAndSociety sac = new StudentAndSociety(rs01.getString(1), rs01.getBoolean(2), rs01.getString(3));
                societiesList.add(sac);
            }
            // 第二次查询，事实证明是可以一个预编译对象多次查询的
            preState.setString(1, "5");
            preState.setString(2, "0");
            ResultSet rs02 = preState.executeQuery();
            while (rs02.next()) {
                StudentAndSociety sac = new StudentAndSociety(rs02.getString(1), rs02.getBoolean(2), rs02.getString(3));
                societiesList.add(sac);
            }
            // 关闭流
            preState.close();
            con.close();
        } catch (Exception e) {
            System.out.println("    发生异常:\n" + e);
        } finally {
            for (StudentAndSociety sac : societiesList) {
                System.out.println(sac);
            }
        }
    }

}

/**
 * 学生与其对应的社团
 */
record StudentAndSociety(String name, boolean sex, String societyName) {
    @Override
    public String toString() {
        return " 姓名：" + String.format("%-7s", name)
                + " 性别：" + (sex ? "男" : "女")
                + " 社团：" + societyName;
    }
}
