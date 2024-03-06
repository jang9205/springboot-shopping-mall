package store.springbootshoppingmall.repository.member;

import org.apache.ibatis.annotations.Param;
import store.springbootshoppingmall.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(MemberSaveDto saveDto);

    void update(Long memberId, MemberUpdateDto updateDto);

    Optional<Member> findById(Long id);

    List<Member> findAll(MemberSearchCond memberSearch);
}
