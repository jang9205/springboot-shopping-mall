package store.springbootshoppingmall.repository.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberUpdateDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address1;

    @NotBlank(message = "상세 주소를 입력해 주세요.")
    private String address2;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String name, String address1, String address2) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
    }
}
