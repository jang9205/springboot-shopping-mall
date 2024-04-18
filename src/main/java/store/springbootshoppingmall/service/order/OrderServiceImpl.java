package store.springbootshoppingmall.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.springbootshoppingmall.domain.*;
import store.springbootshoppingmall.exception.CancelOrderException;
import store.springbootshoppingmall.exception.NotEnoughStockException;
import store.springbootshoppingmall.repository.delivery.DeliveryRepository;
import store.springbootshoppingmall.repository.item.ItemRepository;
import store.springbootshoppingmall.repository.member.MemberRepository;
import store.springbootshoppingmall.repository.order.OrderRepository;
import store.springbootshoppingmall.repository.order.OrderSearchCond;
import store.springbootshoppingmall.repository.orderitem.OrderItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();

        //상품 재고 감소
        int stock = item.getStockQuantity() - count;
        if (stock < 0) {
            throw new NotEnoughStockException("상품의 재고가 부족합니다.");
        }
        item.setStockQuantity(stock);
        itemRepository.updateStock(item);

        //배송정보 생성
        Delivery delivery = Delivery.createDelivery(member);
        deliveryRepository.save(delivery);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문상품, 주문 저장
        orderRepository.save(order);
        orderItemRepository.save(orderItem);

        return order.getId();
    }

    @Override
    @Transactional
    public void cancelOrder(Order order) {
        if (order.getDelivery().getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new CancelOrderException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        } else if (order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new CancelOrderException("이미 취소된 주문입니다.");
        }

        //상품 재고 증가
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            Item item = orderItem.getItem();
            int stock = orderItem.getCount() + item.getStockQuantity();
            item.setStockQuantity(stock);
            itemRepository.updateStock(item);
        }

        //주문 취소
        order.setOrderStatus(OrderStatus.CANCEL);
        orderRepository.cancel(order);

        Delivery delivery = order.getDelivery();
        delivery.setDeliveryStatus(DeliveryStatus.CANCEL);
        deliveryRepository.cancel(delivery);
    }



    @Override
    public Optional<Order> findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> findOrdersByMember(Long memberId) {
        return orderRepository.findByMember(memberId);
    }

    @Override
    public List<Order> findOrders(OrderSearchCond orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
