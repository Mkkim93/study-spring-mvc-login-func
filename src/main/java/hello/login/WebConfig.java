package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean // FilterRegistrationBean : WAS 가 실행될 때 filter 를 넣어준다. (LogFilter 클래스의 로그정보 보임)
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 만든 로그 필터를 넣어준다
        filterRegistrationBean.setOrder(1); // 필터의 순서를 정해준다
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 만든 로그 필터를 넣어준다
        filterRegistrationBean.setOrder(2); // 필터의 순서를 정해준다
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
