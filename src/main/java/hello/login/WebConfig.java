package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 이유가 있어서라기 보다는 스프링이 이 방식으로 제공하기 때문에 이거를 따르면 된다.
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉터를 먹이지 않을 path들
    }

//    @Bean
    public FilterRegistrationBean logFilter() {
        /* 스프링 부트가 WAS를 들고 띄우기 때문에 WAS를 띄울 때 필터를 스프링부트가 같이 띄워준다.*/
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1); // 순서 정해주기. 체인으로 여러개 들어갈 수 있으므로
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 URL 사용

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        /* 스프링 부트가 WAS를 들고 띄우기 때문에 WAS를 띄울 때 필터를 스프링부트가 같이 띄워준다.*/
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2); // 순서 정해주기. 체인으로 여러개 들어갈 수 있으므로
        filterRegistrationBean.addUrlPatterns("/*");
        // 모든 URL 사용. whiteList에서 거르고 있으므로 모두 파라미터로 넣어준다.
        // 여기서 거르는 역할을 안 하면 나중에 미래에 새로운 URL들이 생성되어도 다 필터가 적용되고, whiteList에 필요할 때마다 넣어주면 된다.
        // 메서드 한 번 호출이므로 성능 저하도 거의 없다.

        return filterRegistrationBean;
    }
}
