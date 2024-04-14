package store.springbootshoppingmall.repository.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.Delivery;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryMapper deliveryMapper;

    @Override
    public void save(Delivery delivery) {
        deliveryMapper.save(delivery);
    }

    @Override
    public void cancel(Delivery delivery) {
        deliveryMapper.cancel(delivery);
    }
}
