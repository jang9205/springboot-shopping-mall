package store.springbootshoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.util.SessionConst;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> latestItems = itemService.findLatestItems();
        model.addAttribute("latestItems", latestItems);
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
