package sqlDemo;

import org.apache.ibatis.session.SqlSession;
import myUtils.SqlUtil;
import sqlMapper.StudentsMapper;
import pojo.StudentsMessage;

import java.util.List;

public class StudentsDemo {
    public List<StudentsMessage> allStudent() {
        // 获取SqlSession对象，用于执行sql
        SqlSession session = SqlUtil.getFactory().openSession();
        // 执行sql
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectAll();
        // 释放资源
        session.close();
        return studentsMessages;
    }
}
