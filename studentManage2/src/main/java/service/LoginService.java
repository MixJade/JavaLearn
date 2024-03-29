package service;

import myUtils.SqlUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import pojo.LoginVo;
import sqlMapper.LoginMapper;

public class LoginService {

    public static int userSelect(String nameJade, String passwordJade) {
        SqlSession session = SqlUtil.getFactory().openSession();
        LoginMapper mapper = session.getMapper(LoginMapper.class);
        int haveUser = mapper.queryUserNum(nameJade, passwordJade);
        session.close();
        return haveUser;
    }

    public static boolean userSelectByName(String nameJade) {
        if ("".equals(nameJade)) {
            // 防止有人通过空字符串来进行注册
            return false;
        }
        SqlSession session = SqlUtil.getFactory().openSession();
        LoginMapper mapper = session.getMapper(LoginMapper.class);
        int userSelect = mapper.queryUserByName(nameJade);
        session.close();
        return userSelect == 0;
    }

    public static int addUser(String nameJade, String passwordJade) {
        LoginVo loginVo = new LoginVo(nameJade, passwordJade, false);
        SqlSession session = SqlUtil.getFactory().openSession();
        try {
            LoginMapper mapper = session.getMapper(LoginMapper.class);
            int stu = mapper.addUser(loginVo);
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