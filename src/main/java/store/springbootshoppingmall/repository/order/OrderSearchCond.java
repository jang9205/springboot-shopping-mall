package store.springbootshoppingmall.repository.order;

import lombok.Getter;
import lombok.Setter;
import store.springbootshoppingmall.domain.OrderStatus;

@Getter @Setter
public class OrderSearchCond {

    private String memberName;
    private OrderStatus orderStatus;    //주문 상태 [ORDER,CANCEL]
}
