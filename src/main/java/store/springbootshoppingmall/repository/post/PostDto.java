package store.springbootshoppingmall.repository.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDto {

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "본문을 작성해 주세요.")
    private String content;

    private MultipartFile picture;

    private String picturePath;
}
