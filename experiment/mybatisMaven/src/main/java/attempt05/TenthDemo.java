package attempt05;

import backyard.SqlUtil;
import backyard.StudentsMapper;
import backyard.StudentsMessage;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class TenthDemo {
    /**
     * 动态修改;
     * 允许传的参数为null;
     * 注意映射文件set,if标签
     */
    public void updateVaried(String studentName, Integer englishGrade, Integer mathGrade) {
        StudentsMessage studentsMessage = new StudentsMessage();
        studentsMessage.setStudentName(studentName);
        studentsMessage.setMathGrade(englishGrade);
        studentsMessage.setEnglishGrade(mathGrade);
        SqlSession session = SqlUtil.getFactory().openSession();
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            int stu = mapper.updateVaried(studentsMessage);
            if (stu > 0) System.out.println(" 成功修改了 " + stu + " 行，被修改者：" + studentName);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
            System.out.println("修改失败，事务回滚。");
        }
    }
}
