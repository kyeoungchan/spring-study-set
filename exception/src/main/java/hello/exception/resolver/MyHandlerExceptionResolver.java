package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("call resolver", ex);

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView(); // 새로운 인스턴스를 빈 값으로 넘기면 계속 정상적으로 리턴되면서 WAS까지 return되고, 예외는 삼켜져버린다.
                // 그리고 정상 리턴 후 서블릿 컨테이너(WAS)에서 400으로 온 것을 확인한다.
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
