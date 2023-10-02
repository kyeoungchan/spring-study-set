package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
    // v2에는 서블릿 기술들이 다 들어갔지만, 여기서는 그런 게 없다.
}
