package mix.order.service;

import mix.order.mapper.OrderMapper;
import mix.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Order queryOrderById(Integer orderId) {
        // 查询订单
        return orderMapper.findById(orderId);
    }
}
