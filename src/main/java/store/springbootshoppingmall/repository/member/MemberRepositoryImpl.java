package store.springbootshoppingmall.repository.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Member save(MemberSaveDto saveDto) {
        Member member = new Member();

        member.setEmail(saveDto.getEmail());
        member.setPassword(saveDto.getPassword());
        member.setName(saveDto.getName());
        member.setAddress1(saveDto.getAddress1());
        member.setAddress2(saveDto.getAddress2());
        member.setGrade(saveDto.getGrade());

        memberMapper.save(member);
        log.info("New member id: {}", member.getId());

        return member;
    }

    @Override
    public void update(Long memberId, MemberUpdateDto updateDto) {
        Member member = new Member();

        member.setName(updateDto.getName());
        member.setAddress1(updateDto.getAddress1());
        member.setAddress2(updateDto.getAddress2());

        memberMapper.update(memberId, member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    @Override
    public List<Member> findAll(MemberSearchCond memberSearch) {
        return memberMapper.findAll(memberSearch);
    }
}
