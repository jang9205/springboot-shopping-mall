package store.springbootshoppingmall.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Review {

    private Long id;
    private Item item;
    private String title;
    private String content;
    private LocalDateTime date;
    private int like;
}
