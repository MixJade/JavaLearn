package operateFile;

import java.io.InputStream;
import java.util.Properties;

/**
 * Java可以直接从properties文件中读取信息
 * <p>但properties文件中只能用英文</p>
 */
public class ReadConfig {
    public static void main(String[] args) {
        // 读取与当前类同一个包下的配置文件
        try (InputStream fis = ReadConfig.class.getResourceAsStream("readConfig.properties")) {
            Properties props = new Properties();
            //加载属性列表
            props.load(fis);
            //打印属性
            System.out.println("username: " + props.getProperty("username"));
            System.out.println("password: " + props.getProperty("password"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}