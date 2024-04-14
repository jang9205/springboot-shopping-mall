package store.springbootshoppingmall.repository.orderitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.OrderItem;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemMapper orderItemMapper;


    @Override
    public void save(OrderItem orderItem) {
        orderItemMapper.save(orderItem);
    }
}
