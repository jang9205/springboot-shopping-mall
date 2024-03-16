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
        //이메일 중복 검증
        Optional<Member> existingMember = memberRepository.findByEmail(saveDto.getEmail());
        if (existingMember.isPresent()) {
            return null; //중복된 이메일을 발견한 경우 null 반환
        }

        return memberRepository.save(saveDto);
    }

    @Override
    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);  //패스워드가 맞지 않으면 null 반환
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
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> findAllMembers(MemberSearchCond memberSearch) {
        return memberRepository.findAll(memberSearch);
    }
}
