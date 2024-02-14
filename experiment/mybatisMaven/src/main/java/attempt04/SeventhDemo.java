package attempt04;

import backyard.RankShow;
import backyard.SqlUtil;
import backyard.StudentsMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SeventhDemo {
    /**
     * 条件选择查询;
     * 注意映射文件的choose,when标签;
     * 只是把参数传给noRepeat方法
     */
    public void queryByName(String studentName) {
        System.out.println("查询条件，姓名包含：" + studentName);
        if (studentName != null)
            studentName = "%" + studentName + "%";
        RankShow rankShow = new RankShow();
        rankShow.setStudentName(studentName);
        noRepeat(rankShow);
    }

    /**
     * 单个条件选择;
     * 只是把参数传给noRepeat方法
     */
    public void queryByEnglish(Integer englishGrade) {
        System.out.println("查询条件，英语成绩大于" + englishGrade + "分");
        RankShow rankShow = new RankShow();
        rankShow.setEnglishGrade(englishGrade);
        noRepeat(rankShow);
    }

    /**
     * 单个条件选择;
     * 只是把参数传给noRepeat方法
     */
    public void queryByMath(Integer mathGrade) {
        System.out.println("查询条件，数学成绩大于" + mathGrade + "分");
        RankShow rankShow = new RankShow();
        rankShow.setMathGrade(mathGrade);
        noRepeat(rankShow);
    }

    /**
     * 两个条件的选择
     */
    public void queryByEnglishMath(Integer englishGrade, Integer mathGrade) {
        System.out.println("查询条件，英语大于" + englishGrade + "分，数学大于" + mathGrade + "分");
        RankShow rankShow = new RankShow();
        rankShow.setEnglishGrade(englishGrade);
        rankShow.setMathGrade(mathGrade);
        noRepeat(rankShow);
    }

    /**
     * 执行其他方法传来的参数
     */
    private void noRepeat(RankShow rankShow) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        List<RankShow> studentsMessages = mapper.selectChoice(rankShow);
        System.out.println(studentsMessages);
        session.close();
    }
}
