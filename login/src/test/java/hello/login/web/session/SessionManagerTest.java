package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse(); // 가짜로 기능 테스트를 위해서 HTTP Servlet Response가 제공된다.
        Member member = new Member();
        sessionManager.createSession(member, response); // 웹 브라우저에 응답이 나갔다고 가정하는 문장

        // 요청에 응답 쿠키 저장
        // 여기서부터는 웹 브라우저의 요청이라고 가정한다.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}
