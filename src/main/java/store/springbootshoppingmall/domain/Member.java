package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.repository.member.MemberUpdateDto;

@Getter @Setter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String address1;
    private String address2;
    private MemberGrade grade;  //회원등급 [BASIC, VIP, MANAGER]

    //생성 메서드
    public static Member saveMember(MemberSaveDto saveDto) {
        Member member = new Member();

        member.setEmail(saveDto.getEmail());
        member.setPassword(saveDto.getPassword());
        member.setName(saveDto.getName());
        member.setAddress1(saveDto.getAddress1());
        member.setAddress2(saveDto.getAddress2());
        member.setGrade(saveDto.getGrade());
        return member;
    }

    public static Member updateMember(MemberUpdateDto updateDto) {
        Member member = new Member();

        member.setName(updateDto.getName());
        member.setAddress1(updateDto.getAddress1());
        member.setAddress2(updateDto.getAddress2());
        return member;
    }

}
