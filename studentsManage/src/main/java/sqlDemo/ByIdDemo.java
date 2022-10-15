package sqlDemo;

import mixSQL.SqlUtil;
import mixSQL.UserMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.StudentsTable;

public class ByIdDemo {
    public static StudentsTable selectId(int id) {
        SqlSession session = SqlUtil.getFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        StudentsTable studentsTable = mapper.selectById(id);
        session.close();
        return studentsTable;
    }

    public static int updateSQL(StudentsTable studentsTable) {
        SqlSession session = SqlUtil.getFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            int stu = mapper.updateOrigin(studentsTable);
            if (stu > 0) System.out.println(" 成功修改了 " + stu + " 行");
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
            return stu;
        } catch (PersistenceException e) {
            session.rollback();
            System.out.println("修改失败，事务回滚。"+e);
        }
        return 0;
    }

    public static void deleteOrigin(int id) {
        try {
            SqlSession session = SqlUtil.getFactory().openSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.deleteOrigin(id);
            session.commit();
            session.close();
            System.out.println("删除成功");
        } catch (PersistenceException e) {
            System.out.println("删除失败。\n" + e);
        }
    }
}
