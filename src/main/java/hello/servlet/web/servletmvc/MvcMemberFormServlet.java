package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* MVC 패턴은 항상 Controller를 거쳐서 View로 들어가야 한다.
         * 얘는 Controller 역할을 해줄 것이며, 고객 요청 시 제일 먼저 얘가 호출된다.
         * 제일 처음에는 member-form을 보여줄 것이다.
         * 이 Contorller에서는 JSP로 가주기 위한 경로를 호출한다.
         * 즉, 서버 내부에서 서버끼리 호출하는 셈이다.*/

        System.out.println("MvcMemberFormServlet.service");

        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);// Controller에서 View로 이동할 때 사용하는 것. 이 경로로 이동할 거라는 뜻.
        dispatcher.forward(request, response); // Sevlet에서 JSP를 호출해주는 역할을 한다.

    }
}
