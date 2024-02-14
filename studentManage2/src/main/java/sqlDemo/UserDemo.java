package sqlDemo;

import myUtils.SqlUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.UserMessage;
import sqlMapper.StudentsMapper;

public class UserDemo {

    public static int userSelect(String nameJade ,String passwordJade) {
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        int haveUser = mapper.userSelect(nameJade, passwordJade);
        session.close();
        return haveUser;
    }

    public static boolean userSelectByName(String nameJade) {
        if ("".equals(nameJade)) {
            // 防止有人通过空字符串来进行注册
            return false;
        }
        SqlSession session = SqlUtil.getFactory().openSession();
        StudentsMapper mapper = session.getMapper(StudentsMapper.class);
        int userSelect = mapper.userSelectByName(nameJade);
        session.close();
        return userSelect==0;
    }

    public static int addUser(String nameJade, String passwordJade) {
        UserMessage userMessage = new UserMessage();
        userMessage.setNameJade(nameJade);
        userMessage.setPasswordJade(passwordJade);
        SqlSession session = SqlUtil.getFactory().openSession();
        try {
            StudentsMapper mapper = session.getMapper(StudentsMapper.class);
            int stu = mapper.addUser(userMessage);
            if (stu == 1) System.out.println(" 成功插入 " + nameJade);
            // 事务提交，你懂的，后面还有一个回滚才健全。
            session.commit();
            session.close();
            return stu;
        } catch (PersistenceException e) {
            session.rollback();
            System.out.println(nameJade + "插入失败，事务回滚。");
        }
        return 0;
    }
}