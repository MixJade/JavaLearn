package attempt04;

import backyard.RankShow;
import backyard.SqlUtil;
import backyard.StudentsMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SixthDemo {
    /**
     * 条件缺失查询;
     * 注意映射文件的where,if标签;
     * 注意实体类的参数用Integer
     */
    public void queryLack(String studentName, Integer englishGrade, Integer mathGrade) {
        if (studentName != null)
            studentName = "%" + studentName + "%";
        RankShow rankShow = new RankShow();
        rankShow.setStudentName(studentName);
        rankShow.setEnglishGrade(englishGrade);
        rankShow.setMathGrade(mathGrade);
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<RankShow> studentsMessages = mapper.selectLack(rankShow);
        System.out.println(studentsMessages);
        session.close();
    }
}
