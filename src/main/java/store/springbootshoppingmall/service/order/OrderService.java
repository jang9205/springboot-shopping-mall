package store.springbootshoppingmall.service.order;

import store.springbootshoppingmall.domain.Order;
import store.springbootshoppingmall.repository.order.OrderSearchCond;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Long order(Long memberId, Long itemId, int count);

    void cancelOrder(Order order);

    Optional<Order> findOrderById(Long orderId);

    List<Order> findOrdersByMember(Long memberId);

    List<Order> findOrders(OrderSearchCond orderSearch);
}
