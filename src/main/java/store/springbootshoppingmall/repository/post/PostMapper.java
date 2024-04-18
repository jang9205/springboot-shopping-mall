package store.springbootshoppingmall.repository.post;

import org.apache.ibatis.annotations.Mapper;
import store.springbootshoppingmall.domain.Post;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    void save(Post post);

    Optional<Post> findNoticeById(Long id);

    List<Post> findNoticeAll();

    Optional<Post> findMagazineById(Long id);

    List<Post> findMagazineAll();

}
