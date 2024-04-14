package store.springbootshoppingmall.repository.orderitem;

import org.apache.ibatis.annotations.Mapper;
import store.springbootshoppingmall.domain.OrderItem;

@Mapper
public interface OrderItemMapper {

    void save(OrderItem orderItem);


}
