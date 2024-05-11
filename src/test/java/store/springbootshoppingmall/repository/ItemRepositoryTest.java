package store.springbootshoppingmall.repository;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.springbootshoppingmall.controller.HomeController;
import store.springbootshoppingmall.controller.PostController;
import store.springbootshoppingmall.domain.Category;
import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.repository.item.ItemDto;
import store.springbootshoppingmall.repository.item.ItemRepository;
import store.springbootshoppingmall.repository.item.ItemSearchCond;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.service.post.PostService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest(
        properties = {
        "cloud.aws.credentials.accessKey=test-access-key",
        "cloud.aws.credentials.secretKey=test-secret-key",
        "cloud.aws.region.static=test-region"
})
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @MockBean
    ItemService itemService;

    @MockBean
    AmazonS3 amazonS3;

    @MockBean
    HomeController homeController;

    @MockBean
    PostService postService;

    @MockBean
    PostController postController;

    @Test
    void saveItem() {
        //given

        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        ItemDto itemDto = new ItemDto("Hoodie", "XL", "Black", 80000, "~~~",
                100, multipartFile, Category.Hoodie, "~~/static/img/product/product12.jpg");

        //when
        Item item = itemRepository.save(itemDto);

        //then
        assertThat(item.getName()).isEqualTo(itemDto.getName());
    }

    @Test
    void updateItem() {
        //given

        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        ItemDto itemDto1 = new ItemDto("Hoodie", "XL", "Black", 80000, "~~~",
                100, multipartFile, Category.Hoodie, "~~/static/img/product/product12.jpg");

        Item savedItem = itemRepository.save(itemDto1);
        Long itemId = savedItem.getId();

        //when
        ItemDto itemDto2 = new ItemDto("Hoodie2", "M", "White", 50000, "~",
                500, multipartFile, Category.Hoodie, "~~/static/img/product/product15.jpg");
        itemRepository.update(itemId, itemDto2);

        //then
        Item findItem = itemRepository.findById(itemId).get();
        assertThat(findItem.getName()).isEqualTo(itemDto2.getName());
        assertThat(findItem.getSize()).isEqualTo(itemDto2.getSize());
        assertThat(findItem.getColor()).isEqualTo(itemDto2.getColor());
        assertThat(findItem.getPrice()).isEqualTo(itemDto2.getPrice());
        assertThat(findItem.getContent()).isEqualTo(itemDto2.getContent());
        assertThat(findItem.getStockQuantity()).isEqualTo(itemDto2.getStockQuantity());
        assertThat(findItem.getPicture()).isEqualTo(itemDto2.getPicturePath());
    }

    @Test
    void findAllItems() {
        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        ItemDto itemDto1 = new ItemDto("Hoodie", "XL", "Black", 80000, "~~~",
                100, multipartFile, Category.Hoodie, "~~/static/img/product/product12.jpg");
        ItemDto itemDto2 = new ItemDto("TShirt1", "XL", "Black", 100000, "~~~",
                100, multipartFile, Category.TShirt, "~~/static/img/product/product20.jpg");
        ItemDto itemDto3 = new ItemDto("Tshirt2", "XL", "Black", 150000, "~~~",
                100, multipartFile, Category.TShirt, "~~/static/img/product/product1.jpg");

        Item item1 = itemRepository.save(itemDto1);
        Item item2 = itemRepository.save(itemDto2);
        Item item3 = itemRepository.save(itemDto3);

        //둘 다 없음 검증
        test(null, null, null, item1, item2, item3);
        test("", null, null, item1, item2, item3);

        //name 검증
        test("ir", null, null, item2, item3);
        test("Hood", null, null, item1);

        //price 검증
        test(null, 100000, null, item1, item2);
        test(null, 99999, null, item1);
        test(null, 150001, null, item1, item2, item3);
        test(null, 150000, null, item1, item2, item3);
        test(null, 149999, null, item1, item2);
        test(null, 80000, null, item1);
        test(null, 79999, null);

        test(null, null, 150000, item3);
        test(null, null, 150001);
        test(null, null, 100000, item2, item3);
        test(null, null, 100001, item3);
        test(null, null, 80000, item1, item2, item3);
        test(null, null, 80001, item2, item3);

        test(null, 120000, 100000, item2);
        test(null, 150000, 100000, item2, item3);
        test(null, 150000, 100001, item3);

        //둘 다 검증
        test("ir", 100000, null, item2);
        test("ir", 99999, null);
        test("TShirt", 150000, 100000, item2, item3);
        test("TShirt", 149999, 100001);
        test("di", 80000, 80000, item1);
    }

    void test(String name, Integer maxPrice, Integer minPrice, Item... items) {
        ItemSearchCond itemSearch = new ItemSearchCond(name, maxPrice, minPrice);
        List<Item> result = itemRepository.findAll(itemSearch);
        assertThat(result).hasSize(items.length);
    }
}
