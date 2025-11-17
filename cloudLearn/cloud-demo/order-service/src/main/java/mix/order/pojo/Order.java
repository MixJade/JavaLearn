package mix.order.pojo;

import lombok.Data;

@Data
public class Order {
    private Integer orderId;
    private Integer userId;
    private String orderName;
    private Long price;
    private Integer num;
    // 对应用户的数据
    private User user;
}