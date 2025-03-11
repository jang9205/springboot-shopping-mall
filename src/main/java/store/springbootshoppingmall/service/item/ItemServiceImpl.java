package store.springbootshoppingmall.service.item;

import com.amazonaws.services.s3.AmazonS3;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public Item saveItem(ItemDto itemDto) {
        try {
            //사진 저장 로직
            MultipartFile picture = itemDto.getPicture();

            if (picture != null && !picture.isEmpty()) { //파일이 업로드된 경우에만 처리
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

                //S3에 파일 업로드
                amazonS3.putObject(bucketName, "product/" + fileName, picture.getInputStream(), null);

                //S3에 저장된 파일의 키를 저장
                itemDto.setPicturePath(bucketName + ".s3.ap-northeast-2.amazonaws.com/product/" + fileName);
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

                //S3에 파일 업로드
                amazonS3.putObject(bucketName, "product/" + fileName, picture.getInputStream(), null);

                itemDto.setPicturePath(bucketName + ".s3.ap-northeast-2.amazonaws.com/product/" + fileName);
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

    @Override
    public List<Item> findLatestItems() {
        return itemRepository.findLatestItems();
    }
}
