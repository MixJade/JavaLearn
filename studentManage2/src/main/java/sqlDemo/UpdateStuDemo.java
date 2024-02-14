package sqlDemo;


import myUtils.SqlUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.StudentsTable;
import sqlMapper.StudentsMapper;

public class UpdateStuDemo {

    /**
     * 修改学生信息
     */
    public static int updateStuTable(StudentsTable studentsTable) {
        SqlSession session = SqlUtil.getFactory().openSession();
        int stu = 0;
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            stu = mapper.updateOrigin(studentsTable);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
        }
        return stu;
    }
}
