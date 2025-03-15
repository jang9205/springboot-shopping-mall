package store.springbootshoppingmall.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.util.SessionConst;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loginName")
    public String getMemberName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return null;
        }
        return loginMember.getName();
    }
}
