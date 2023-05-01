package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; // static 사용
    /* 멤버들이 static이어서, 아무리 MemberRepository 객체가 많아도 각 변수들이 하나다.
     * 사실 이 클래스 자체가 싱글톤이기 때문에 static이 없어도 되지만, 하나인 것을 보장하기 위해 사용한다.*/

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {
        /*싱글톤이기 위해서는 private으로 생성자를 막아줘야 한다.*/
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // new ArrayList를 건들거나 조작하더라도 stores에 있는 value들을 건드리지 않기 위함이다.
    }

    public void clearStore() {
        store.clear();
    }
}
