package store.springbootshoppingmall.service.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.exception.FileStorageException;
import store.springbootshoppingmall.repository.item.ItemDto;
import store.springbootshoppingmall.repository.item.ItemRepository;
import store.springbootshoppingmall.repository.item.ItemSearchCond;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Value("${file.dir1}")
    private String fileDir;

    @Override
    public Item saveItem(ItemDto itemDto) {
        try {
            // 사진 저장 로직
            MultipartFile picture = itemDto.getPicture();

            if (picture != null && !picture.isEmpty()) { // 파일이 업로드된 경우에만 처리
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
                String fullPath = fileDir + fileName;
                picture.transferTo(new File(fullPath));

                itemDto.setPicturePath("/img/product/" + fileName);
            }

            return itemRepository.save(itemDto);
        } catch (IOException e) {
            throw new FileStorageException("사진 파일 등록에 실패했습니다.", e);
        }
    }

    @Override
    public void updateItem(Long itemId, ItemDto itemDto) {
        try {
            MultipartFile picture = itemDto.getPicture();

            if (picture != null && !picture.isEmpty()) {
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
                String fullPath = fileDir + fileName;
                picture.transferTo(new File(fullPath));

                itemDto.setPicturePath("/img/product/" + fileName);
            }

            itemRepository.update(itemId, itemDto);
        } catch (IOException e) {
            throw new FileStorageException("사진 파일 등록에 실패했습니다.", e);
        }

    }

    @Override
    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findAllItems(ItemSearchCond itemSearch) {
        return itemRepository.findAll(itemSearch);
    }
}
