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
import store.springbootshoppingmall.repository.member.MemberLoginDto;
import store.springbootshoppingmall.repository.member.MemberSaveDto;
import store.springbootshoppingmall.service.member.MemberService;
import store.springbootshoppingmall.util.SessionConst;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

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

        //이메일 중복 체크
        Member member = memberService.join(saveDto);
        if (member == null) {
            result.rejectValue("email", "duplicate.email", "이미 존재하는 이메일입니다.");
            return "members/join";
        }

        redirectAttributes.addFlashAttribute("successMessage", "회원가입 성공!");
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

        model.addAttribute("loginMember", loginMember);
        return "members/mypage";
    }

}
