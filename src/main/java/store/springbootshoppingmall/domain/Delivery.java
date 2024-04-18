package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Delivery {

    private Long id;
    private String address1;
    private String address2;
    private DeliveryStatus deliveryStatus;  //배송상태 [READY, COMP, CANCEL]

    public static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setAddress1(member.getAddress1());
        delivery.setAddress2(member.getAddress2());
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        return delivery;
    }
}
