package attempt03;

import backyard.RankShow;
import backyard.SqlUtil;
import backyard.StudentsMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MybatisFourthDemo {
    /**
     * 开始模糊查询;
     * 通过对象封装参数;
     * 注意映射语句中的like
     */
    public void queryDimObject(String studentName, Integer englishGrade, Integer mathGrade) {
        // 处理参数，因为学生姓名用的是模糊查询，要换成表达式的形式
        studentName = "%" + studentName + "%";
        // 封装对象，这是用实体类封装参数，要跟参数名对应上
        RankShow rankShow = new RankShow();
        rankShow.setStudentName(studentName);
        rankShow.setEnglishGrade(englishGrade);
        rankShow.setMathGrade(mathGrade);
        // 开始查询
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<RankShow> studentsMessages = mapper.selectDimObject(rankShow);
        System.out.println(studentsMessages);
        session.close();

    }
}
