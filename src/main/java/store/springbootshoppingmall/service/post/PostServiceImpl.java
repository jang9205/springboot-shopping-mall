package store.springbootshoppingmall.service.post;

import com.amazonaws.services.s3.AmazonS3;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public Post saveNotice(Long memberId, PostDto postDto) {
        Member member = memberRepository.findById(memberId).get();

        try {
            //사진 저장 로직
            MultipartFile picture = postDto.getPicture();

            if (picture != null && !picture.isEmpty()) {
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

                //S3에 파일 업로드
                amazonS3.putObject(bucketName, "post/" + fileName, picture.getInputStream(), null);

                //S3에 저장된 파일의 키를 저장
                postDto.setPicturePath(bucketName + ".s3.ap-northeast-2.amazonaws.com/post/" + fileName);
            }

            Post post = Post.createPost(member, postDto);
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

            if (picture != null && !picture.isEmpty()) {
                String originalFilename = picture.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

                //S3에 파일 업로드
                amazonS3.putObject(bucketName, "post/" + fileName, picture.getInputStream(), null);

                //S3에 저장된 파일의 키를 저장
                postDto.setPicturePath(bucketName + ".s3.ap-northeast-2.amazonaws.com/post/" + fileName);
            }

            Post post = Post.createPost(member, postDto);
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
