package mixUtils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlUtil {
    // 一个实用工具类，通过静态代码块的加载来减少代码复用
    // 不能在这里配置SqlSession，因为session是被操作的对象，会影响多线程的使用
    private static  SqlSessionFactory sessionFactory;
    static {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
            sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (IOException e){
            System.out.println("配置文件加载失败:\n"+e);
        }
    }

    public static SqlSessionFactory getFactory(){
        return sessionFactory;
    }
}
