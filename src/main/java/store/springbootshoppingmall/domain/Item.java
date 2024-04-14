package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;
import store.springbootshoppingmall.exception.NotEnoughStockException;
import store.springbootshoppingmall.repository.item.ItemDto;

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

    // 생성 메서드
    public static Item createItem(ItemDto itemDto) {
        Item item = new Item();

        item.setName(itemDto.getName());
        item.setSize(itemDto.getSize());
        item.setColor(itemDto.getColor());
        item.setPrice(itemDto.getPrice());
        item.setContent(itemDto.getContent());
        item.setStockQuantity(itemDto.getStockQuantity());
        item.setPicture(itemDto.getPicturePath());
        item.setCategory(itemDto.getCategory());
        return item;
    }
}
