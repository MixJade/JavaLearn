package sqlMapper;

import org.apache.ibatis.annotations.Param;
import pojo.LoginVo;


public interface LoginMapper {

    // 登录时查询人是否存在
    int queryUserNum(@Param("nameJade") String nameJade, @Param("passwordJade") String passwordJade);

    // 注册时查询是否重名
    int queryUserByName(@Param("nameJade") String nameJade);

    // 注册插入人员
    int addUser(LoginVo loginVo);
}
