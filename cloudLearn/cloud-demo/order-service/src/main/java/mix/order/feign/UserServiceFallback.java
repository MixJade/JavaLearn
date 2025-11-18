package mix.order.feign;

import mix.order.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

// 必须加@Component，注册为Bean
@Component
public class UserServiceFallback implements FallbackFactory<UserServiceFeignClient> {
    private static final Logger log = LoggerFactory.getLogger(UserServiceFallback.class);

    @Override
    public UserServiceFeignClient create(Throwable cause) {
        log.error("异常原因:{}", cause.getMessage(), cause);
        //noinspection Convert2Lambda
        return new UserServiceFeignClient() {
            // 降级逻辑：查询用户失败时返回默认用户
            @Override
            public User getUserById(Integer id) {
                User defaultUser = new User();
                defaultUser.setUserId(0);
                defaultUser.setUsername("默认用户（服务暂时不可用）");
                return defaultUser;
            }
        };
    }

}
