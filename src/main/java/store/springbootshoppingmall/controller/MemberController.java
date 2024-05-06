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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.domain.MemberGrade;
import store.springbootshoppingmall.domain.Order;
import store.springbootshoppingmall.repository.member.MemberLoginDto;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.repository.member.MemberSearchCond;
import store.springbootshoppingmall.repository.member.MemberUpdateDto;
import store.springbootshoppingmall.service.member.MemberService;
import store.springbootshoppingmall.service.order.OrderService;
import store.springbootshoppingmall.util.SessionConst;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("saveDto") MemberSaveDto saveDto) {
        return "members/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("saveDto") MemberSaveDto saveDto,
                       BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "members/join";
        }

        Member member = memberService.join(saveDto);

        //이메일 중복 체크
        if (member == null) {
            result.rejectValue("email", "duplicate.email", "이미 존재하는 이메일입니다.");
            return "members/join";
        }

        redirectAttributes.addFlashAttribute("successMessage", "회원가입 성공!");
        log.info("Join - Member Id: {}", member.getId());

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") MemberLoginDto loginDto) {
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") MemberLoginDto loginDto, BindingResult result,
                        HttpServletRequest request) {

        if (result.hasErrors()) {
            return "members/login";
        }

        Member loginMember = memberService.login(loginDto.getEmail(), loginDto.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "members/login";
        }

        //로그인 성공 처리
        HttpSession session = request.getSession(); //세션이 있으면 반환, 없으면 생성해서 반환
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        log.info("Login - Member Id: {}", loginMember.getId());

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); //세션이 있으면 반환

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }

        if (loginMember.getGrade() == MemberGrade.MANAGER) {
            model.addAttribute("manager", loginMember.getGrade());
        }

        List<Order> orders = orderService.findOrdersByMember(loginMember.getId());

        model.addAttribute("loginMember", loginMember);
        model.addAttribute("orders", orders);
        return "members/mypage";
    }

    @GetMapping("/mypage/update")
    public String updateForm(@ModelAttribute("updateDto") MemberUpdateDto updateDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }
        return "members/update";
    }

    @PostMapping("/mypage/update")
    public String update(@Valid @ModelAttribute("updateDto") MemberUpdateDto updateDto, BindingResult result,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "members/update";
        }

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }

        memberService.updateMember(loginMember.getId(), updateDto);

        // 업데이트된 회원 정보 가져오기
        Member member = memberService.findMemberById(loginMember.getId()).get();

        // 업데이트된 회원 정보로 세션 업데이트
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        redirectAttributes.addFlashAttribute("successMessage", "배송지가 수정되었습니다.");
        return "redirect:/mypage";
    }

    @GetMapping("/memberList")
    public String memberList(@ModelAttribute("memberSearch") MemberSearchCond memberSearch, Model model,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        List<Member> members = memberService.findAllMembers(memberSearch);
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
