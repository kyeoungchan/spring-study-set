package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // 스프링 부트가 앤티티 매니저를 주입을 해줄 수 있게 하는 애너테이션이다.
    private EntityManager em;

    /**
     * Member를 반환하지 않고 memberId를 반환하는 이유
     * 커맨드와 쿼리를 분리하기 위해서다.
     * 저장하고 Side Effect를 안 일으키게 하기 위해서 id 정도만 조회를 시킨다.
     * id 정도 있으면 다음에 다시 조회할 수 있으니까 id 정도만 조회하는 것으로 주로 설계를 한다.
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
