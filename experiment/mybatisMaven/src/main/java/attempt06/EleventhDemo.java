package attempt06;

import backyard.SqlUtil;
import backyard.StudentsMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class EleventhDemo {
    /**
     * 第一个删除语句;
     * 仍然默认事务
     */
    public void deleteOrigin(String studentName) {
        try {
            SqlSession session = SqlUtil.getFactory().openSession();
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            mapper.deleteOrigin(studentName);
            session.commit();
            session.close();
            System.out.println("删除 " + studentName + " 成功");
        } catch (PersistenceException e) {
            System.out.println("删除失败。\n" + e);
        }
    }
}
