package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null이면 로그인 실패
     */
    public Member login(String loginId, String password) {

/*
        // 일단 loginId를 던져서 회원이 있는지 없는지를 먼저 찾는다.
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get(); // get()말고 다른 걸 쓰는 게 좋다. 없으면 예외터짐.
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }
*/

        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
        
    }
}
