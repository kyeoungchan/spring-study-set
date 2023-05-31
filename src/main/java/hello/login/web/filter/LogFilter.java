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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /* HttpServletRequest가 아닌 ServletRequest인데, ServletRequest가 더 상위계층, 즉 부모다.
        * 그런데 기능이 거의 없기 때문에 다운 캐스팅을 해줘야 한다.
        * 원래 서블릿이 요청을 받을 때 HTTP 외의 요청도 받을 수 있도록 설계가 됐기 때문에 발생한다.*/
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString(); // 요청들을 구분하기 위한 uuid. 사용자 구분이 아니다.

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 다음 필터가 있으면 다음 필터 호출, 없으면 서블릿 호출
        } catch (Exception e) {
            throw e;
        } finally{
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }

    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
