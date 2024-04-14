package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItem {

    private Long id;
    private Item item;
    private Order order;
    private int orderPrice; //주문 가격
    private int count;  //주문 수량

    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        return orderItem;
    }

}
