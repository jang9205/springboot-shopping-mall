package store.springbootshoppingmall.repository.order;

import store.springbootshoppingmall.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    void cancel(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByMember(Long memberId);

    List<Order> findAll(OrderSearchCond orderSearch);
}
