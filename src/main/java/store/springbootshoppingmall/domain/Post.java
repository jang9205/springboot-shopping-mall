package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

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
    private List<String> pictures = new ArrayList<>();
}
