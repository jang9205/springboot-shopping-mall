package store.springbootshoppingmall.repository.item;

import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
public class ItemSearchCond {

    private String name;

    private Integer maxPrice;

    private Integer minPrice;

    public ItemSearchCond() {
    }

    public ItemSearchCond(String name, Integer maxPrice, Integer minPrice) {
        this.name = name;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }
}
