package store.springbootshoppingmall.repository.item;

import store.springbootshoppingmall.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(ItemDto itemDto);

    void update(Long itemId, ItemDto itemDto);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearchCond);

    void updateStock(Item item);

    List<Item> findLatestItems();
}
