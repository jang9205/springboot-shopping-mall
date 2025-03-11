package store.springbootshoppingmall.service.item;

import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.repository.item.ItemDto;
import store.springbootshoppingmall.repository.item.ItemSearchCond;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item saveItem(ItemDto itemDto);

    void updateItem(Long itemId, ItemDto itemDto);

    Optional<Item> findItemById(Long id);

    List<Item> findAllItems(ItemSearchCond itemSearch);

    List<Item> findLatestItems();
}
