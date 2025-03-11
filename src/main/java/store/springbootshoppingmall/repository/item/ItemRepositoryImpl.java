package store.springbootshoppingmall.repository.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.exception.NotEnoughStockException;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemMapper itemMapper;

    @Override
    public Item save(ItemDto itemDto) {
        Item item = Item.createItem(itemDto);
        itemMapper.save(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemDto itemDto) {
        Item item = Item.createItem(itemDto);
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

    @Override
    public void updateStock(Item item) {
        itemMapper.updateStock(item);
    }

    @Override
    public List<Item> findLatestItems() {
        return itemMapper.findLatestItems();
    }
}
