package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Delivery {

    private Long id;
    private String address1;
    private String address2;
    private DeliveryStatus deliveryStatus;  //배송상태 [READY, COMP, CANCEL]
}
