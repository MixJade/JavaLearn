package attempt05;

import backyard.SqlUtil;
import backyard.StudentsMapper;
import backyard.StudentsMessage;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class EighthDemo {
    /**
     * 第一个插入操作;
     * 注意默认涉及事务;
     * 在映射文件设置了返回主键
     */
    public void addSQL(String studentName, int sex, int englishGrade, int mathGrade, double height, String birthday, int money) {
        StudentsMessage studentsMessage = new StudentsMessage();
        studentsMessage.setMessage(studentName, sex, englishGrade, mathGrade, height, birthday, money);
        SqlSession session = SqlUtil.getFactory().openSession();
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            int stu = mapper.addOrigin(studentsMessage);
            if (stu == 1) System.out.println(" 成功插入 " + studentName + " 插入Id是：" + studentsMessage.getId());
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
            System.out.println(studentName + "插入失败，事务回滚。");
        }
    }
}
