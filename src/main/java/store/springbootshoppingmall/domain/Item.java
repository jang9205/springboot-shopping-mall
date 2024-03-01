package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Item {

    public Long id;
    public String name;
    public String size;
    public String color;
    public int price;
    public String content;
    public int stockQuantity;
    public List<String> pictures = new ArrayList<>();
    public Category category;
}
