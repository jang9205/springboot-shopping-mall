package store.springbootshoppingmall.repository.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import store.springbootshoppingmall.domain.MemberGrade;

@Data
public class MemberSaveDto {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "영문, 숫자를 포함하여 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address1;

    @NotBlank(message = "상세 주소를 입력해 주세요.")
    private String address2;

    private MemberGrade grade = MemberGrade.BASIC;  //기본값 설정

    public MemberSaveDto() {
    }

    public MemberSaveDto(String email, String password, String name, String address1, String address2) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
    }
}
