package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import lombok.extern.java.Log;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

// 필터를 등록하는 클래스 (WebConfig)
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    /**
     * 서블릿 인터셉터 등록 (요청 로그 / 인증 로그)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1) // 로직의 순서 1
                .addPathPatterns("/**") // 모든 경로를 허용한다는 뜻
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 지정한 경로는 허용 하지 않는다는 뜻
        // 즉, 모든 경로(/**)는 허용되지만 모든 경로 중 지정한 경로 ("/css/**", "/*.ico", "/error") 는 허용하지 않는다.

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2) // 로직의 순서 2
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
    }

    /**
     *  로그인 서블릿 필터 (요청 로그)
     */
//    @Bean // FilterRegistrationBean : WAS 가 실행될 때 filter 를 넣어준다. (LogFilter 클래스의 로그정보 보임)
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 만든 로그 필터를 넣어준다
        filterRegistrationBean.setOrder(1); // 필터의 순서를 정해준다
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    /**
     * 로그인 서블릿 필터 (인증 체크)
     */
    //@Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 만든 로그 필터를 넣어준다
        filterRegistrationBean.setOrder(1); // 필터의 순서를 정해준다
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
