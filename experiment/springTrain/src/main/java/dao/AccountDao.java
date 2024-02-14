package dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao {
    @Update("update societys set account = account + #{account} where societyName = #{name}")
    void add(@Param("account") Double money, @Param("name") String name);

    @Update("update societys set account = account - #{account} where societyName = #{name}")
    void reduce(@Param("account") Double money, @Param("name") String name);
}
