package mix.user.mapper;

import mix.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    
    @Select("select * from demo_user where user_id = #{id}")
    User findById(@Param("id") Integer id);
}