package store.springbootshoppingmall.repository;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.springbootshoppingmall.controller.HomeController;
import store.springbootshoppingmall.controller.PostController;
import store.springbootshoppingmall.domain.ContentCategory;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.domain.Post;
import store.springbootshoppingmall.repository.member.MemberRepository;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.repository.post.PostDto;
import store.springbootshoppingmall.repository.post.PostRepository;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.service.post.PostService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest(
        properties = {
                "cloud.aws.credentials.accessKey=test-access-key",
                "cloud.aws.credentials.secretKey=test-secret-key",
                "cloud.aws.region.static=test-region"
        })
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @MockBean
    ItemService itemService;

    @MockBean
    AmazonS3 amazonS3;

    @MockBean
    HomeController homeController;

    @MockBean
    PostService postService;

    @MockBean
    PostController postController;

    @Test
    void savePost() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");

        Member savedMember = memberRepository.save(memberSaveDto);

        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        PostDto postDto = new PostDto("서버 점검 공지", "~~", multipartFile, "/static/post/post1.jpg");

        Post post = Post.createPost(savedMember, postDto);
        post.setCategory(ContentCategory.NOTICE);

        //when
        Post savedPost = postRepository.save(post);

        //then
        assertThat(postDto.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(postDto.getContent()).isEqualTo(savedPost.getContent());
        assertThat(postDto.getPicturePath()).isEqualTo(savedPost.getPicture());
    }

    @Test
    void findNoticeById() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");

        Member savedMember = memberRepository.save(memberSaveDto);

        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        PostDto postDto = new PostDto("서버 점검 공지", "~~", multipartFile, "/static/post/post1.jpg");

        Post post = Post.createPost(savedMember, postDto);
        post.setCategory(ContentCategory.NOTICE);

        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        //when
        Post findPost = postRepository.findNoticeById(postId).get();

        //then
        assertThat(findPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(findPost.getContent()).isEqualTo(savedPost.getContent());
        assertThat(findPost.getPicture()).isEqualTo(savedPost.getPicture());
        assertThat(findPost.getCategory()).isEqualTo(ContentCategory.NOTICE);
    }

    @Test
    void findMagazineById() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");

        Member savedMember = memberRepository.save(memberSaveDto);

        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        PostDto postDto = new PostDto("서버 점검 공지", "~~", multipartFile, "/static/post/post1.jpg");

        Post post = Post.createPost(savedMember, postDto);
        post.setCategory(ContentCategory.MAGAZINE);

        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        //when
        Post findPost = postRepository.findMagazineById(postId).get();

        //then
        assertThat(findPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(findPost.getContent()).isEqualTo(savedPost.getContent());
        assertThat(findPost.getPicture()).isEqualTo(savedPost.getPicture());
        assertThat(findPost.getCategory()).isEqualTo(ContentCategory.MAGAZINE);
    }

    @Test
    void findNoticeAll() {
        //given
        MemberSaveDto memberSaveDto = new MemberSaveDto("qqq1111@gmail.com", "rewq1235698",
                "kim", "서울 송파구 올림픽로 300", "401-12");

        Member savedMember = memberRepository.save(memberSaveDto);

        //가짜 파일 데이터 생성
        byte[] fileBytes = "Test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("testFile", "test.txt",
                "text/plain", fileBytes);

        PostDto postDto1 = new PostDto("서버 점검 공지1", "~~~", multipartFile, "/static/post/post1.jpg");
        PostDto postDto2 = new PostDto("서버 점검 공지2", "~~", multipartFile, "/static/post/post2.jpg");
        PostDto postDto3 = new PostDto("매거진", "~~", multipartFile, "/static/post/post3.jpg");

        Post post1 = Post.createPost(savedMember, postDto1);
        post1.setCategory(ContentCategory.NOTICE);

        Post post2 = Post.createPost(savedMember, postDto2);
        post2.setCategory(ContentCategory.NOTICE);

        Post post3 = Post.createPost(savedMember, postDto3);
        post3.setCategory(ContentCategory.MAGAZINE);

        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        Post savedPost3 = postRepository.save(post3);

        //when
        List<Post> noticeAll = postRepository.findNoticeAll();

        //then
        assertThat(noticeAll).hasSize(2);
        assertThat(noticeAll.get(0).getTitle()).isEqualTo(postDto2.getTitle());
        assertThat(noticeAll.get(1).getTitle()).isEqualTo(postDto1.getTitle());
    }
}
