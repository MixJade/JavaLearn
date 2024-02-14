package sqlDemo;

import mixUtils.SqlUtil;
import mixUtils.UserMapper;
import pojo.UserMessage;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class UserDemo {

    public static UserMessage userSelect(String nameJade, String passwordJade) {
        SqlSession session = SqlUtil.getFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserMessage userSelect = mapper.userSelect(nameJade, passwordJade);
        session.close();
        return userSelect;
    }

    public static boolean userSelectByName(String nameJade) {
        if ("".equals(nameJade)) {
            // 防止有人通过空字符串来进行注册
            return false;
        }
        SqlSession session = SqlUtil.getFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        UserMessage userSelect = mapper.userSelectByName(nameJade);
        session.close();
        return userSelect == null;
    }

    public static void addUser(String nameJade, String passwordJade) {
        UserMessage userMessage = new UserMessage();
        userMessage.setUser(nameJade, passwordJade);
        SqlSession session = SqlUtil.getFactory().openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int stu = mapper.addUser(userMessage);
            if (stu == 1) System.out.println(" 成功插入 " + nameJade);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
        } catch (PersistenceException e) {
            session.rollback();
            System.out.println(nameJade + "插入失败，事务回滚。");
        }
    }
}