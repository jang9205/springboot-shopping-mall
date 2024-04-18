package store.springbootshoppingmall.service.post;

import store.springbootshoppingmall.domain.Post;
import store.springbootshoppingmall.repository.post.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post saveNotice(Long memberId, PostDto postDto);

    Optional<Post> findNoticeById(Long id);

    List<Post> findNoticeAll();

    Post saveMagazine(Long memberId, PostDto postDto);

    Optional<Post> findMagazineById(Long id);

    List<Post> findMagazineAll();
}
