package jpabook.start.ex1.persistence;

import jpabook.start.ex1.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DirtyCheckingTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member(10L , "DirtyChecking");
            member.setName("DirtyChecking2");

            tx.commit();
            // 변경 감지
            // 영속성 컨텍스트내에 변경이 있으면 커밋 시점에 update query 가 날아간다
            // update query 도 쓰기지연 저장소에 저장되서 한번에 날아간
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
