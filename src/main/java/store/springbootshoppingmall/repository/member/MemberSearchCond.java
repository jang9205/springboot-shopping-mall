package store.springbootshoppingmall.repository.member;

import lombok.Data;

@Data
public class MemberSearchCond {

    private String email;
    private String name;

    public MemberSearchCond() {
    }

    public MemberSearchCond(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
