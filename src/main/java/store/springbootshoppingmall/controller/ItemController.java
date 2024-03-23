package store.springbootshoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.springbootshoppingmall.domain.Category;
import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.domain.MemberGrade;
import store.springbootshoppingmall.repository.item.ItemDto;
import store.springbootshoppingmall.repository.item.ItemSearchCond;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.util.SessionConst;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/save")
    public String saveForm(@ModelAttribute("itemDto") ItemDto itemDto, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        List<Category> categoryList = Arrays.asList(Category.values());
        model.addAttribute("categoryList", categoryList);
        return "items/save";
    }

    @PostMapping("/items/save")
    public String saveItem(@Valid @ModelAttribute("itemDto") ItemDto itemDto,
                           BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "items/save";
        }

        Item item = itemService.saveItem(itemDto);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addFlashAttribute("successMessage", "상품이 성공적으로 등록되었습니다.");

        return "redirect:/products/{itemId}";
    }

    @GetMapping("/shop")
    public String items(@ModelAttribute("itemSearch") ItemSearchCond itemSearch, BindingResult result, Model model,
                        HttpServletRequest request) {

        if (result.hasErrors()) {
            return "items/shop";
        }

        //세션에서 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
            if (loginMember != null && loginMember.getGrade() == MemberGrade.MANAGER) {
                model.addAttribute("manager", MemberGrade.MANAGER);
            }
        }

        //상품 목록 가져오기
        List<Item> items = itemService.findAllItems(itemSearch);
        model.addAttribute("items", items);
        return "items/shop";
    }

    @GetMapping("/products/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findItemById(itemId).get();
        model.addAttribute("item", item);
        return "items/item";
    }

}
