package JDBCAttempt;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Properties;

public class CrudUtil {
    private static DataSource dataSource;
    static {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/JDBCAttempt/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            System.out.println("文件没有找到:\n" + e);
        }
    }
    public static DataSource druidGet(){
        return dataSource;
    }
}
