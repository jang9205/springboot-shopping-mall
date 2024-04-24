package store.springbootshoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.springbootshoppingmall.domain.*;
import store.springbootshoppingmall.repository.order.OrderSearchCond;
import store.springbootshoppingmall.service.item.ItemService;
import store.springbootshoppingmall.service.order.OrderService;
import store.springbootshoppingmall.util.SessionConst;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping("/products/{itemId}/order")
    public String orderForm(@PathVariable("itemId") Long itemId, @RequestParam("count") int count,
                            HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            redirectAttributes.addFlashAttribute("needLogin", "로그인 후에 이용 가능합니다.");
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            redirectAttributes.addFlashAttribute("needLogin", "로그인 후에 이용 가능합니다.");
            return "redirect:/login";
        }

        Item item = itemService.findItemById(itemId).get();

        model.addAttribute("loginMember", loginMember);
        model.addAttribute("item", item);
        model.addAttribute("count", count);
        return "order/orderForm";
    }

    @PostMapping("/products/{itemId}/order")
    public String order(@PathVariable("itemId") Long itemId, @RequestParam("count") int count,
                        HttpServletRequest request, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            redirectAttributes.addFlashAttribute("needLogin", "로그인 후에 이용 가능합니다.");
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            redirectAttributes.addFlashAttribute("needLogin", "로그인 후에 이용 가능합니다.");
            return "redirect:/login";
        }

        Long orderId = orderService.order(loginMember.getId(), itemId, count);
        redirectAttributes.addAttribute("orderId", orderId);
        redirectAttributes.addFlashAttribute("successOrder", "상품 주문이 완료되었습니다.");
        return "redirect:/order_detail/{orderId}";
    }

    @GetMapping("order_detail/{orderId}")
    public String orderPage(@PathVariable("orderId") Long orderId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Order order = orderService.findOrderById(orderId).get();
        Member orderMember = order.getMember();

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }
        if (loginMember.getGrade() == MemberGrade.MANAGER || loginMember.getId().equals(orderMember.getId())) {
            model.addAttribute("order", order);

            //날짜 포맷팅
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss");
            String formattedDate = order.getDate().format(formatter);
            model.addAttribute("formattedDate", formattedDate);

            return "order/orderDetail";
        }

        return "redirect:/";
    }

    @PostMapping("order_detail/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes,
                              HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Order order = orderService.findOrderById(orderId).get();
        Member orderMember = order.getMember();

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }
        if (loginMember.getGrade() == MemberGrade.MANAGER || loginMember.getId().equals(orderMember.getId())) {
            orderService.cancelOrder(order);
            redirectAttributes.addAttribute("orderId", orderId);
            redirectAttributes.addFlashAttribute("cancelOrder", "주문이 취소되었습니다.");
            return "redirect:/order_detail/{orderId}";
        }
        return "redirect:/";
    }

    @GetMapping("/orderList")
    public String orderList(@ModelAttribute("orderSearch") OrderSearchCond orderSearch, Model model,
                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        List<OrderStatus> statusList = Arrays.asList(OrderStatus.values());
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("statusList", statusList);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
}
