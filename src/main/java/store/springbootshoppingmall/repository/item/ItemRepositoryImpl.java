package store.springbootshoppingmall.repository.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.Item;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemMapper itemMapper;

    @Override
    public Item save(ItemDto itemDto) {
        Item item = getItem(itemDto);
        itemMapper.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemDto itemDto) {
        Item item = getItem(itemDto);
        itemMapper.update(itemId, item);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemMapper.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond itemSearch) {
        return itemMapper.findAll(itemSearch);
    }

    private static Item getItem(ItemDto itemDto) {
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
