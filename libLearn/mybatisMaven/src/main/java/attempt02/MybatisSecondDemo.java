package attempt02;

import backyard.StudentsMapper;
import backyard.StudentsMessage;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisSecondDemo {
    /**
     * 开始通过接口查询;
     * 依然没有用工具类
     */
    public void demo02() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 获取SqlSession对象，用于执行sql
            SqlSession session = sessionFactory.openSession();
            // 获取studentsMapper接口代理对象，就这里不一样了
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            List<StudentsMessage> studentsMessages = mapper.selectAll();
            System.out.println(studentsMessages);
            // 释放资源
            session.close();
        } catch (IOException e) {
            System.out.println("配置文件加载失败：\n" + e);
        }
    }
}
