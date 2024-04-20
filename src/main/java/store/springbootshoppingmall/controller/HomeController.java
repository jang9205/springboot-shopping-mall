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
import store.springbootshoppingmall.repository.item.ItemSearchCond;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.util.SessionConst;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(Model model) {
        ItemSearchCond itemSearchCond = new ItemSearchCond();
        List<Item> items = itemService.findAllItems(itemSearchCond);

        // id 값이 큰 순서로 정렬하고 상위 4개만 필터링
        List<Item> latestItems = items.stream()
                .sorted((item1, item2) -> Long.compare(item2.getId(), item1.getId()))
                .limit(4)
                .collect(Collectors.toList());

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
