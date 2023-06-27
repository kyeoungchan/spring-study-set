package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException { // save
        // repository.save에서 checked Exception이 올라오기 때문에 처리를 해야한다.

        Member member = new Member("memberV0", 10000);
        repository.save(member);
    }
}