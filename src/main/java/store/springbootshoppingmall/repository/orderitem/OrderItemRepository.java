package store.springbootshoppingmall.repository.orderitem;

import store.springbootshoppingmall.domain.OrderItem;

public interface OrderItemRepository {

    void save(OrderItem orderItem);
}
