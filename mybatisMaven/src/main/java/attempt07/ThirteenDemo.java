package attempt07;

import backyard.SqlUtil;
import backyard.StudentsMapper;
import backyard.StudentsMessage;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ThirteenDemo {
    /**
     * 通过接口处的注解定义sql语句;
     * 不通过映射文件;
     * 实现方法跟之前没有区别
     */
    public void queryByExplainId(int id) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectByExplain(id);
        System.out.println(studentsMessages);
        session.close();
    }

    /**
     * 看，确实没区别
     */
    public void queryByExplainSociety(String societyName) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectByExplainSociety(societyName);
        System.out.println(studentsMessages);
        session.close();
    }
}
