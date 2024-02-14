package sqlDemo;

import myUtils.SqlUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.StudentsMessage;
import pojo.StudentsTable;
import sqlMapper.StudentsMapper;

import java.util.List;


public class SelectStuDemo {
    /**
     * 分页查询，每七个一页
     *
     * @param studentName 学生姓名,模糊查询
     * @param societyId   社团id,模糊查询
     * @param begin       索引开始位置
     * @param pageItem    一页的条目数
     * @return 大量学生信息
     */
    public static List<StudentsMessage> selectPage(String studentName, Integer societyId, int begin, int pageItem) {
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
    public static int selectCount(String studentName, Integer societyId) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        int studentCount = mapper.selectCount(studentName, societyId);
        session.close();
        return studentCount;
    }

    /**
     * 根据id查询学生
     *
     * @param id 学生id
     * @return 学生信息
     */
    public static StudentsTable selectId(int id) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        StudentsTable studentsTable = mapper.selectById(id);
        session.close();
        return studentsTable;
    }

    /**
     * 直接查询所有学生
     *
     * @return 所有学生带着社团名字的信息
     */
    public static List<StudentsMessage> allStudent() {
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
