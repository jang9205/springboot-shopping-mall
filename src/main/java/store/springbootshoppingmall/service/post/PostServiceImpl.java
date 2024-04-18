package store.springbootshoppingmall.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import store.springbootshoppingmall.domain.ContentCategory;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.domain.Post;
import store.springbootshoppingmall.exception.FileStorageException;
import store.springbootshoppingmall.repository.member.MemberRepository;
import store.springbootshoppingmall.repository.post.PostDto;
import store.springbootshoppingmall.repository.post.PostRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Value("${file.dir2}")
    private String fileDirNotice;

    @Value("${file.dir3}")
    private String fileDirMagazine;

    @Override
    public Post saveNotice(Long memberId, PostDto postDto) {
        Member member = memberRepository.findById(memberId).get();

        try {
            // 사진 저장 로직
            MultipartFile picture = postDto.getPicture();

            if (picture != null && !picture.isEmpty()) { // 파일이 업로드된 경우에만 처리
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
                String fullPath = fileDirNotice + fileName;
                picture.transferTo(new File(fullPath));

                postDto.setPicturePath("/img/notice/" + fileName);
            }

            Post post = Post.creatPost(member, postDto);
            post.setCategory(ContentCategory.NOTICE);

            return postRepository.save(post);

        } catch (IOException e) {
            throw new FileStorageException("사진 파일 등록에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<Post> findNoticeById(Long id) {
        return postRepository.findNoticeById(id);
    }

    @Override
    public List<Post> findNoticeAll() {
        return postRepository.findNoticeAll();
    }

    @Override
    public Post saveMagazine(Long memberId, PostDto postDto) {
        Member member = memberRepository.findById(memberId).get();

        try {
            // 사진 저장 로직
            MultipartFile picture = postDto.getPicture();

            if (picture != null && !picture.isEmpty()) { // 파일이 업로드된 경우에만 처리
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
                String fullPath = fileDirMagazine + fileName;
                picture.transferTo(new File(fullPath));

                postDto.setPicturePath("/img/magazine/" + fileName);
            }

            Post post = Post.creatPost(member, postDto);
            post.setCategory(ContentCategory.MAGAZINE);

            return postRepository.save(post);

        } catch (IOException e) {
            throw new FileStorageException("사진 파일 등록에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<Post> findMagazineById(Long id) {
        return postRepository.findMagazineById(id);
    }

    @Override
    public List<Post> findMagazineAll() {
        return postRepository.findMagazineAll();
    }
}
