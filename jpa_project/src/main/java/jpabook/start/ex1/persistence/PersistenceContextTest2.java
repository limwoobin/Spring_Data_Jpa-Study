package jpabook.start.ex1.persistence;

import jpabook.start.ex1.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContextTest2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member memberA = new Member();
            memberA.setId(1L);
            memberA.setName("A");

            Member memberB = new Member();
            memberB.setId(2L);
            memberB.setName("B");

            em.persist(memberA);
            em.persist(memberB);
            // 여기까지 insert sql 을 보내지 않는다.
            // 영속성 컨텍스트의 쓰기지연 저장소에 query가 저장된다

            tx.commit();
            // commit 시점에 쓰기지연 저장소에 있는 query 들이 flush 되면서 query 가 날라간다
            // jdbc batch 라 말한다
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
