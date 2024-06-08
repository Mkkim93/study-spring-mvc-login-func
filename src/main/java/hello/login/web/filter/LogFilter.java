package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }


    // 요청이 올때마다 doFilter 가 먼저 호출이 된다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request; // 다운캐스팅
        String requestURI = httpRequest.getRequestURI(); // 모든 사용자들의 url 기록
        String uuid = UUID.randomUUID().toString();

        try {
          log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 현재 필터가 종료되고 다음에 실행될 필터 또는 서블릿을 호출한다.
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
