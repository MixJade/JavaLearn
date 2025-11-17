package mix.order.mapper;

import mix.order.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    @Select("select * from demo_order where order_id = #{id}")
    Order findById(Integer id);
}
