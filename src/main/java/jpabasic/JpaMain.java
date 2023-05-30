package jpabasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {
            // 영속
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZ");
//            em.persist(member); // 불필요한 코드
            System.out.println("=============");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 데이터베이스를 물고 돌아가기 때문에 꼭 닫아줘야한다.
        }
        emf.close();
    }
}