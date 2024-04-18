package store.springbootshoppingmall.repository.post;

import store.springbootshoppingmall.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findNoticeById(Long id);

    List<Post> findNoticeAll();

    Optional<Post> findMagazineById(Long id);

    List<Post> findMagazineAll();
}
