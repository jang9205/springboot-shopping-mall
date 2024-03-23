package store.springbootshoppingmall.repository.item;

import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
public class ItemSearchCond {

    private String name;

    @Digits(integer = 0, fraction = 0, message = "숫자를 입력하세요.")
    private Integer maxPrice;

    @Digits(integer = 0, fraction = 0, message = "숫자를 입력하세요.")
    private Integer minPrice;

    public ItemSearchCond() {
    }

    public ItemSearchCond(String name, Integer maxPrice, Integer minPrice) {
        this.name = name;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }
}
