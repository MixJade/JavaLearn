package attempt03;

import backyard.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MybatisThirdDemo {
    /**
     * 尝试单条件查询;
     * 开始使用自己写的工具类
     */
    public void queryById(int id) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectById(id);
        System.out.println(studentsMessages);
        session.close();
    }

    /**
     * 还是单条件查询
     */
    public void queryBySociety(String societyName) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<StudentsMessage> studentsMessages = mapper.selectBySociety(societyName);
        System.out.println(studentsMessages);
        session.close();
    }

    /**
     * 开始多条件查询
     * 注意接口中通过param注解来定义映射参数
     */
    public void queryByTwoSixty(int englishGrade, int mathGrade) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<RankShow> studentsMessages = mapper.selectByTwoSixty(englishGrade, mathGrade);
        System.out.println(studentsMessages);
        session.close();
    }
}
