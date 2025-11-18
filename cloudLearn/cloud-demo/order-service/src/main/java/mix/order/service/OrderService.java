package mix.order.service;

import mix.order.feign.UserServiceFeignClient;
import mix.order.mapper.OrderMapper;
import mix.order.pojo.Order;
import mix.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    // 注入Feign接口
    private final UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public OrderService(OrderMapper orderMapper, UserServiceFeignClient userServiceFeignClient) {
        this.orderMapper = orderMapper;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    public Order queryOrderById(Integer orderId) {
        // 查询订单
        Order order = orderMapper.findById(orderId);
        // 调用feign
        if (order != null && order.getUserId() != null) {
            User userById = userServiceFeignClient.getUserById(order.getUserId());
            order.setUser(userById);
        }
        return order;
    }
}
