package jpabook.start.ex11;

import jpabook.start.ex10.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("user1");
            em.persist(member);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class , member.getId());
            System.out.println(refMember.getClass());   // proxy

            em.detach(refMember);   // 영속성 제거 (준영속화)
            System.out.println(refMember.getName());    // 영속성 컨텍스트 밖에서 프록시 초기화 -> 에러발생

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
