package store.springbootshoppingmall.repository.delivery;

import org.apache.ibatis.annotations.Mapper;
import store.springbootshoppingmall.domain.Delivery;

@Mapper
public interface DeliveryMapper {

    void save(Delivery delivery);

    void cancel(Delivery delivery);
}
