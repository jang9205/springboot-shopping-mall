package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String address1;
    private String address2;
    private MemberGrade grade;  //회원등급 [BASIC, VIP, MANAGER]
}
