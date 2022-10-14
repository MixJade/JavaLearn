package attempt05;

import backyard.SqlUtil;
import backyard.StudentsMapper;
import backyard.StudentsMessage;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class NinthDemo {
    /**
     * 死板的静态修改;
     * 当然默认涉及事务
     */
    public void updateSQL(String studentName, int englishGrade, int mathGrade) {
        StudentsMessage studentsMessage = new StudentsMessage();
        studentsMessage.setStudentName(studentName);
        studentsMessage.setMathGrade(englishGrade);
        studentsMessage.setEnglishGrade(mathGrade);
        //SqlUtil.getFactory()
        SqlSession session = SqlUtil.getFactory().openSession();
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            int stu = mapper.updateOrigin(studentsMessage);
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
