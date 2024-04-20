package store.springbootshoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.springbootshoppingmall.domain.Member;
import store.springbootshoppingmall.domain.MemberGrade;
import store.springbootshoppingmall.domain.Post;
import store.springbootshoppingmall.repository.post.PostDto;
import store.springbootshoppingmall.service.post.PostService;
import store.springbootshoppingmall.util.SessionConst;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/notice/save")
    public String saveNoticeForm(@ModelAttribute("postDto") PostDto postDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        return "posts/saveNotice";
    }

    @PostMapping("/notice/save")
    public String saveNotice(@Valid @ModelAttribute("postDto") PostDto postDto, HttpServletRequest request,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "posts/saveNotice";
        }
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        Post post = postService.saveNotice(loginMember.getId(), postDto);
        redirectAttributes.addAttribute("postId", post.getId());
        redirectAttributes.addFlashAttribute("successMessage", "공지사항이 등록되었습니다.");
        return "redirect:/notice/{postId}";
    }

    @GetMapping("/notice")
    public String noticeList(HttpServletRequest request, Model model) {
        //세션에서 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
            if (loginMember != null && loginMember.getGrade() == MemberGrade.MANAGER) {
                model.addAttribute("manager", loginMember.getGrade());
            }
        }

        List<Post> notices = postService.findNoticeAll();
        model.addAttribute("notices", notices);
        return "posts/noticeList";
    }

    @GetMapping("/notice/{postId}")
    public String noticeDetail(@PathVariable("postId") Long postId, Model model) {
        Post notice = postService.findNoticeById(postId).get();

        //날짜 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = notice.getDate().format(formatter);

        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("notice", notice);
        return "posts/noticeDetail";
    }

    @GetMapping("/magazine/save")
    public String saveMagazineForm(@ModelAttribute("postDto") PostDto postDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        return "posts/saveMagazine";
    }

    @PostMapping("/magazine/save")
    public String saveMagazine(@Valid @ModelAttribute("postDto") PostDto postDto, HttpServletRequest request,
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "posts/saveMagazine";
        }
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null || loginMember.getGrade() != MemberGrade.MANAGER) {
            return "redirect:/";
        }

        Post post = postService.saveMagazine(loginMember.getId(), postDto);
        redirectAttributes.addAttribute("postId", post.getId());
        redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
        return "redirect:/magazine/{postId}";
    }

    @GetMapping("/magazine")
    public String magazineList(HttpServletRequest request, Model model) {
        //세션에서 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
            if (loginMember != null && loginMember.getGrade() == MemberGrade.MANAGER) {
                model.addAttribute("manager", loginMember.getGrade());
            }
        }

        List<Post> magazines = postService.findMagazineAll();
        model.addAttribute("magazines", magazines);
        return "posts/magazineList";
    }

    @GetMapping("/magazine/{postId}")
    public String magazineDetail(@PathVariable("postId") Long postId, Model model) {
        Post magazine = postService.findMagazineById(postId).get();

        //날짜 포맷팅
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = magazine.getDate().format(formatter);

        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("magazine", magazine);
        return "posts/magazineDetail";
    }
}
