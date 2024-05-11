package store.springbootshoppingmall.service;

import com.amazonaws.services.s3.AmazonS3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.springbootshoppingmall.controller.HomeController;
import store.springbootshoppingmall.controller.PostController;
import store.springbootshoppingmall.domain.*;
import store.springbootshoppingmall.exception.NotEnoughStockException;
import store.springbootshoppingmall.repository.item.ItemDto;
import store.springbootshoppingmall.repository.item.ItemRepository;
import store.springbootshoppingmall.repository.member.MemberRepository;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.service.order.OrderService;
import store.springbootshoppingmall.service.post.PostService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest(
        properties = {
                "cloud.aws.credentials.accessKey=test-access-key",
                "cloud.aws.credentials.secretKey=test-secret-key",
                "cloud.aws.region.static=test-region"
        })
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    MemberRepository memberRepository;

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
    void orderSuccess() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");
        Member member = memberRepository.save(memberSaveDto);

        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        ItemDto itemDto = new ItemDto("Hoodie", "XL", "Black", 80000, "~~~",
                100, multipartFile, Category.Hoodie, "~~/static/img/product/product12.jpg");
        Item item = itemRepository.save(itemDto);

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 10);
        Order order = orderService.findOrderById(orderId).get();

        //then
        assertThat(order.getMember().getName()).isEqualTo(member.getName());
        assertThat(order.getOrderItems().get(0).getItem().getName()).isEqualTo(item.getName());
        assertThat(order.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(90);
        assertThat(order.getOrderItems().get(0).getCount()).isEqualTo(10);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getDelivery().getAddress1()).isEqualTo(member.getAddress1());
        assertThat(order.getDelivery().getAddress2()).isEqualTo(member.getAddress2());
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
    }

    @Test
    void orderFail() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");
        Member member = memberRepository.save(memberSaveDto);

        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        ItemDto itemDto = new ItemDto("Hoodie", "XL", "Black", 80000, "~~~",
                100, multipartFile, Category.Hoodie, "~~/static/img/product/product12.jpg");
        Item item = itemRepository.save(itemDto);


        //when and then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), 1000);
        });
    }

    @Test
    void orderCancel() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");
        Member member = memberRepository.save(memberSaveDto);

        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        ItemDto itemDto = new ItemDto("Hoodie", "XL", "Black", 80000, "~~~",
                100, multipartFile, Category.Hoodie, "~~/static/img/product/product12.jpg");
        Item item = itemRepository.save(itemDto);

        Long orderId = orderService.order(member.getId(), item.getId(), 10);
        Order order = orderService.findOrderById(orderId).get();

        //when
        orderService.cancelOrder(order);
        Order cancelOrder = orderService.findOrderById(orderId).get();

        //then
        assertThat(cancelOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(100);
        assertThat(cancelOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(cancelOrder.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.CANCEL);
    }
}
