package store.springbootshoppingmall.repository.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.springbootshoppingmall.domain.Post;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        postMapper.save(post);
        return post;
    }

    @Override
    public Optional<Post> findNoticeById(Long id) {
        return postMapper.findNoticeById(id);
    }

    @Override
    public List<Post> findNoticeAll() {
        return postMapper.findNoticeAll();
    }

    @Override
    public Optional<Post> findMagazineById(Long id) {
        return postMapper.findMagazineById(id);
    }

    @Override
    public List<Post> findMagazineAll() {
        return postMapper.findMagazineAll();
    }
}
