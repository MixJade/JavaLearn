package mix.order.feign;

import mix.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * user-service的api接口
 */
@FeignClient(name = "user-service", fallback = UserServiceFallback.class, path = "/api")
public interface UserServiceFeignClient {
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Integer id);
}
