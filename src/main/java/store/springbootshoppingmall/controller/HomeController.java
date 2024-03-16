package store.springbootshoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.util.SessionConst;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

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
