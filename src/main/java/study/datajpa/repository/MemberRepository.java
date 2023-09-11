package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username); // 메서드 명은 자유롭게 짜도 된다.

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
//    Slice<Member> findByAge(in t age, Pageable pageable);
//    List<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 이게 있어야 JPA가 getResult()가 아닌 executeUpdate()로 나간다.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @EntityGraph(attributePaths = "team")
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // 공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = "team")
    List<Member> findAll();

    // JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = "team")
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 메서드 이름으로 쿼리에서 특히 편하다.
    @EntityGraph(attributePaths = "team")
    List<Member> findEntityGraphByUsername(String username);
    // find 와 By 사이에는 아무거나 적어도 문제없이 동작한다.

    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberNamedEntityGraph();
}
