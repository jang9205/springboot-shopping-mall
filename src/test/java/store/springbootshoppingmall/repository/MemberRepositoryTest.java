package store.springbootshoppingmall.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.domain.MemberGrade;
import store.springbootshoppingmall.repository.member.MemberRepository;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.repository.member.MemberSearchCond;
import store.springbootshoppingmall.repository.member.MemberUpdateDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveMember() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");

        //when
        Member savedMember = memberRepository.save(memberSaveDto);

        //then
        assertThat(savedMember.getEmail()).isEqualTo(memberSaveDto.getEmail());
        assertThat(savedMember.getPassword()).isEqualTo(memberSaveDto.getPassword());
        assertThat(savedMember.getName()).isEqualTo(memberSaveDto.getName());
        assertThat(savedMember.getAddress1()).isEqualTo(memberSaveDto.getAddress1());
        assertThat(savedMember.getAddress2()).isEqualTo(memberSaveDto.getAddress2());
        assertThat(savedMember.getGrade()).isEqualTo(MemberGrade.BASIC);
    }

    @Test
    void updateMember() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");
        Member savedMember = memberRepository.save(memberSaveDto);
        Long memberId = savedMember.getId();

        //when
        MemberUpdateDto memberUpdateDto = new MemberUpdateDto("park", "서울", "111");
        memberRepository.update(memberId, memberUpdateDto);

        //then
        Member findMember = memberRepository.findById(memberId).get();
        assertThat(findMember.getName()).isEqualTo(memberUpdateDto.getName());
        assertThat(findMember.getAddress1()).isEqualTo(memberUpdateDto.getAddress1());
        assertThat(findMember.getAddress2()).isEqualTo(memberUpdateDto.getAddress2());
    }

    @Test
    void findAllMembers() {
        //given
        MemberSaveDto memberSaveDto1 = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");
        MemberSaveDto memberSaveDto2 = new MemberSaveDto("1234@naver.com", "rewq1235698",
                "park", "서울 송파구 올림픽로 300", "401-12");
        MemberSaveDto memberSaveDto3 = new MemberSaveDto("qqq1234@naver.com", "rewq1235698",
                "papa", "서울 송파구 올림픽로 300", "401-12");

        Member member1 = memberRepository.save(memberSaveDto1);
        Member member2 = memberRepository.save(memberSaveDto2);
        Member member3 = memberRepository.save(memberSaveDto3);

        //둘 다 없음 검증
        test(null, null, member1, member2, member3);
        test("", "", member1, member2, member3);

        //email 검증
        test("1234", null, member2, member3);
        test("gmail", null, member1);
        test("naver.com", null, member2, member3);

        //name 검증
        test(null, "pa", member2, member3);
        test(null, "i", member1);

        //둘 다 있음 검증
        test("qqq", "kim", member1);
    }

    void test(String email, String name, Member... members) {
        MemberSearchCond memberSearch = new MemberSearchCond(email, name);
        List<Member> result = memberRepository.findAll(memberSearch);
        assertThat(result).hasSize(members.length);
    }
}
