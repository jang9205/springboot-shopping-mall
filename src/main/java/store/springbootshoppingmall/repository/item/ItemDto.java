package store.springbootshoppingmall.repository.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import store.springbootshoppingmall.domain.Category;

@Data
public class ItemDto {

    @NotBlank(message = "상품 이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "사이즈를 입력해 주세요.")
    private String size;

    @NotBlank(message = "색상을 입력해 주세요.")
    private String color;

    private int price;

    @NotBlank(message = "상품 설명을 입력해 주세요.")
    private String content;

    private int stockQuantity;

    private MultipartFile picture;

    private Category category;

    private String picturePath;

    public ItemDto() {
    }

    public ItemDto(String name, String size, String color, int price, String content, int stockQuantity,
                   MultipartFile picture, Category category, String picturePath) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.content = content;
        this.stockQuantity = stockQuantity;
        this.picture = picture;
        this.category = category;
        this.picturePath = picturePath;
    }
}
