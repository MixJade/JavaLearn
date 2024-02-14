package attempt06;

import backyard.SqlUtil;
import backyard.StudentsMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.util.Arrays;

public class TwelveDemo {
    /**
     * 批量删除;
     * 接口通过@Param重写默认数组名array；
     * 映射语句foreach遍历数组
     */
    public void deleteGroup(String[] nameGroup) {
        try {
            SqlSession session = SqlUtil.getFactory().openSession();
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            mapper.deleteGroup(nameGroup);
            session.commit();
            session.close();
            System.out.println("删除 " + Arrays.toString(nameGroup) + " 成功");
        } catch (PersistenceException e) {
            System.out.println("删除失败。\n" + e);
        }
    }
}
