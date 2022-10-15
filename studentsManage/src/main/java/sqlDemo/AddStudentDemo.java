package sqlDemo;

import mixSQL.SqlUtil;
import mixSQL.UserMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.StudentsTable;

public class AddStudentDemo {

    public static int addStudentTable(StudentsTable studentsTable) {
        SqlSession session = SqlUtil.getFactory().openSession();
        int stu = 0;
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            stu = mapper.addStudent(studentsTable);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
        }
        return stu;
    }
}
