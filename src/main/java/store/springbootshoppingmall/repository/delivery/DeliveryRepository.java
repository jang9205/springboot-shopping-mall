package store.springbootshoppingmall.repository.delivery;

import store.springbootshoppingmall.domain.Delivery;

public interface DeliveryRepository {

    void save(Delivery delivery);

    void cancel(Delivery delivery);
}
