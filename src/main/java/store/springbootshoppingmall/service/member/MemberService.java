package store.springbootshoppingmall.service.member;

import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.repository.member.MemberSearchCond;
import store.springbootshoppingmall.repository.member.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member join(MemberSaveDto saveDto);

    Member login(String email, String password);

    void updateMember(Long memberId, MemberUpdateDto updateDto);

    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByEmail(String email);

    List<Member> findAllMembers(MemberSearchCond memberSearch);
}
