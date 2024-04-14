package store.springbootshoppingmall.repository.order;

import org.apache.ibatis.annotations.Mapper;
import store.springbootshoppingmall.domain.Order;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OrderMapper {

    void save(Order order);

    void cancel(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByMember(Long memberId);

    List<Order> findAll(OrderSearchCond orderSearch);
}
