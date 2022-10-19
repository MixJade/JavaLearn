package sqlDemo;

import myUtils.SqlUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.PageBirth;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import sqlMapper.StudentsMapper;

import java.util.List;

public class SelectPageDemo {
    /**
     * 分页查询，每七个一页
     *
     * @param pageBirth 里面四个参数，定义见这个类的注释
     * @return 学生信息
     */
    public static List<StudentsMessage> selectPage(String studentName,Integer societyId,int begin,int pageItem) {
        // 开始查询
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectByPage(studentName, societyId, begin, pageItem);
        session.close();
        return studentsMessages;
    }

    /**
     * 查询学生人数
     *
     * @return 学生的总数
     */
    public static int selectCount(String studentName,Integer societyId) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        int studentCount = mapper.selectCount(studentName, societyId);
        session.close();
        return studentCount;
    }
}
