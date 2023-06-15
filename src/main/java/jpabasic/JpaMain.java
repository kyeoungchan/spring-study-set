package jpabasic;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===================START====================");
            Member findMember = em.find(Member.class, member.getId());

            // homeCity -> newCity로 변경 의도
//            findMember.getHomeAddress().setCity("newCity"); // 값 타입은 불변해야하므로 절대 이렇게 작성하면 안 된다.
//            Address address = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", address.getStreet(), address.getZipcode())); // 값 타입은 통으로 갈아끼워야 한다.

            // 치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");
            // String 자체도 값 타입이기 때문에 갈아끼워야 한다.

            /*// old1 -> newCity1 주소 변경
            findMember.getAddressHistory().remove(new Address("old1", "street", "10000")); // equals()와 hashCode() 메서드를 확실히 구현을 해야 remove()가 작동한다.
            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));*/

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close(); // 데이터베이스를 물고 돌아가기 때문에 꼭 닫아줘야한다.
        }
        emf.close();
    }

}