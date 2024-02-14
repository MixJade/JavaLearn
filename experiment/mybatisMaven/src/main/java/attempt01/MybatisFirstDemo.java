package attempt01;

import backyard.StudentsMessage;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisFirstDemo {
    /**
     * 只通过映射文件而不通过接口;
     * 同时这里是唯二没有用工具类的
     */
    public void demo01() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 获取SqlSession对象，用于执行sql
            SqlSession session = sessionFactory.openSession();
            // 执行sql
            List<StudentsMessage> studentsMessages = session.selectList("backyard.StudentsMapper.selectAll");
            System.out.println(studentsMessages);
            // 释放资源
            session.close();
        } catch (IOException e) {
            System.out.println("配置文件加载失败：\n" + e);
        }
    }
}
