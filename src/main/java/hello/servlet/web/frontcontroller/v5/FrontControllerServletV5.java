package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    //    private Map<String, ControllerV4> controllerMap = new HashMap<>(); // 비교하기 위한 FrontControllerV4에서 사용하던 controllerMap이다.
    private final Map<String, Object> handelrMappingMap = new HashMap<>(); // V3든, V4든 다 들어갈 수 있게 하기 위해서 Object로 Value 지네릭 타입을 지정하였다.
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>(); // 여러 개의 어댑터 중 하나를 꺼내쓰기 위함이다.

    public FrontControllerServletV5() {

        initHandlerMappingMap();
        initHandlerAdapters();

    }

    private void initHandlerMappingMap() {
        handelrMappingMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        handelrMappingMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        handelrMappingMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request); // 메서드명으로 깔끔하게 처리된다.

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName(); // 논리이름
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {

        /* Mapping 정보를 갖고 Handler를 찾는 메서드
         * 이름 부여용이다.
         * handler를 반환해줘야 한다.
         *
         * 디테일 로직
         * 1. request URI를 찾고
         * 2. request URI를 통해 매핑되는 handler를 찾아서 반환한다.*/

        String requestURI = request.getRequestURI();
        return handelrMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {

        /* HandlerAdapter를 찾아오는 메서드*/

        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다.");
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
