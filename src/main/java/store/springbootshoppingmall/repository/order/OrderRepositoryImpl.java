package store.springbootshoppingmall.repository.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.Order;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order) {
        orderMapper.save(order);
        return order;
    }

    @Override
    public void cancel(Order order) {
        orderMapper.cancel(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderMapper.findById(id);
    }

    @Override
    public List<Order> findByMember(Long memberId) {
        return orderMapper.findByMember(memberId);
    }

    @Override
    public List<Order> findAll(OrderSearchCond orderSearch) {
        return orderMapper.findAll(orderSearch);
    }
}
