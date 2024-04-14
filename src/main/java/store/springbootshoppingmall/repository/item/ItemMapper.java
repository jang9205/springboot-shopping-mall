package store.springbootshoppingmall.repository.item;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.springbootshoppingmall.domain.Item;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("item") Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);

    void updateStock(Item item);
}
