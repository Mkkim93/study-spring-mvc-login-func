package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member")Member member, LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        log.info("login 성공");

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) { // 로그인 정보가 일치 하지 않을 때 global error 발생
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치 하지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리 TODO (= 진행중인 로직이거나 추가 작업이 필요한 로직을 표시)
        // cookie 에는 String 타입이 입력되야하기 때문에 getId 인 Long 타입을 String.valueOf() 를 통해 문자열 타입으로 변환
        // 쿠키에 시간 정보를 주지 않으면 세션 쿠기(브라우저 종료 시 모두 종료)
        // 쿠키 이름 : memberId, 값 : 회원의 아이디 (Id) <- 입력하는 아이디 아님 기본 키
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/"; // 로그인 성공 시 홈으로 이동
    }
}
