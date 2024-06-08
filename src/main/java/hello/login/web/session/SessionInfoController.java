package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        // 세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId()); // sessionID 명
        log.info("MaxInactiveInterval={}", session.getMaxInactiveInterval()); // 비활성화 시키는 시간 (초)
        log.info("creationTime={}", new Date(session.getCreationTime())); // 세션 생성 일자
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); // 세션에 마지막으로 접속한 시간
        log.info("isNew={}", session.isNew()); // 새로 생성된 세선인지 확인 (새로 생성되엇으면 true, 기존 생성 세션이면 false)

        return "세션 출력";
    }
}
