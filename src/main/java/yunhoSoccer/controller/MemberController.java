package yunhoSoccer.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yunhoSoccer.domain.Member;
import yunhoSoccer.domain.MemberDto;
import yunhoSoccer.repo.MemberRepository;
import yunhoSoccer.service.LoginService;
import yunhoSoccer.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/member/new")
    public String joinForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/register";
    }

    @PostMapping("/member/new")
    public String join(@ModelAttribute @Valid MemberDto memberDto) {
        memberService.join(memberDto);
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute @Valid LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "member/login";
        }

        Member loginMember = loginService.login(loginForm.getUserId(), loginForm.getUserPw());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/login";
        }

        request.setAttribute(loginMember.getUserId(), SessionConstant.LOGIN_MEMBER);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/members")
    public List<FindMemberDto> memberList() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(FindMemberDto::new).toList();
    }

    @Data
    public static class FindMemberDto {

        private String userId;
        private String name;
        private int age;
        private String city;
        private String street;
        private String auth;

        public FindMemberDto(Member member) {
            userId = member.getUserId();
            name = member.getName();
            age = member.getAge();
            city = member.getCity();
            street = member.getStreet();
            auth = member.getAuth();
        }
    }
}
