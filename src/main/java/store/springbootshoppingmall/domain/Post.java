package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;
import store.springbootshoppingmall.repository.post.PostDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Post {

    private Long id;
    private Member member;
    private String title;
    private String content;
    private LocalDateTime date;
    private ContentCategory category;   //카테고리 [NOTICE, MAGAZINE]
    private String picture;

    //생성 메서드
    public static Post creatPost(Member member, PostDto postDto) {
        Post post = new Post();

        post.setMember(member);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDate(LocalDateTime.now());
        post.setPicture(postDto.getPicturePath());
        return post;
    }
}
