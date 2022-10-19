package sqlDemo;

import myUtils.SqlUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import sqlMapper.StudentsMapper;

import java.util.Arrays;

public class DeleteIdGroupDemo {
    /**
     * 批量删除;
     * 接口通过@Param重写默认数组名array；
     * 映射语句foreach遍历数组
     */
    public static String deleteGroup(int[] idGroup) {
        try {
            SqlSession session = SqlUtil.getFactory().openSession();
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            mapper.deleteIdGroup(idGroup);
            session.commit();
            session.close();
            return "YES";
        } catch (PersistenceException e) {
            System.out.println("删除失败。\n" + e);
        }
        return "NO";
    }
}
