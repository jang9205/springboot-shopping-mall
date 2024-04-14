package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Order {

    private Long id;
    private Member member;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Delivery delivery;
    private LocalDateTime date; //주문시간
    private OrderStatus orderStatus; //주문상태 [ORDER, CANCEL]

    //연관관계 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setDate(LocalDateTime.now());
        return order;
    }
}
