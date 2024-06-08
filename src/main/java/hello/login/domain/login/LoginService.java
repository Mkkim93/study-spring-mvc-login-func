package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    // 로그인 시 회원가입 조건 확인 (핵심 로직 설계)
    public Member login(String loginId, String password) {

        // Optional 객체를 사용하여 기존 가입 회원 여부를 확인
//        Optional<Member> findByMemberOptional = memberRepository.findByLoginId(loginId);
//        Member member = findByMemberOptional.get();
//
//        if (member.getPassword().equals(password)) {
//            return member;
//        } else {
//            return null;
//        }

        /** !핵심 로직 : 회원 로그인 시 회원 가입된 패스워드를 기준으로 비교하여 패스워드를 loginId 를 기준으로 입력한 패스워드가 동일하면
         정상적으로 동작하고, 아니면 null 을 반환한다.
         (null 이면 로그인 실패)
         */

        return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password))
                  .orElse(null);
    }
}
