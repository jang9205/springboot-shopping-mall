package store.springbootshoppingmall.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.repository.member.MemberRepository;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.repository.member.MemberSearchCond;
import store.springbootshoppingmall.repository.member.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member join(MemberSaveDto saveDto) {
        return memberRepository.save(saveDto);
    }

    @Override
    public void updateMember(Long memberId, MemberUpdateDto updateDto) {
        memberRepository.update(memberId, updateDto);
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findAllMembers(MemberSearchCond memberSearch) {
        return memberRepository.findAll(memberSearch);
    }
}
