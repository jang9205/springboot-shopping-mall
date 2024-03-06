package store.springbootshoppingmall.repository.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.springbootshoppingmall.domain.Member;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(Member member);

    void update(@Param("id") Long id, @Param("updateDto") MemberUpdateDto updateDto);

    Optional<Member> findById(Long id);

    List<Member> findAll(MemberSearchCond memberSearch);
}
