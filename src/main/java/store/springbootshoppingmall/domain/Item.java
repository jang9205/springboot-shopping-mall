package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;
import store.springbootshoppingmall.exception.NotEnoughStockException;

@Getter @Setter
public class Item {

    private Long id;
    private String name;
    private String size;
    private String color;
    private int price;
    private String content;
    private int stockQuantity;
    private String picture;
    private Category category;

    //비즈니스 로직
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
