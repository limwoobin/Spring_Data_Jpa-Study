package jpabook.start.persistence;

import jpabook.start.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DetachTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member(150L , "Detach Test");
            member.setName("ZZZZZ");

            em.detach(member);
//            em.clear();   영속성 컨텍스트를 통째로 지움
//            em.clear();   영속성 컨텍스트 종료

            System.out.println("====================================");
            tx.commit();
            // 변경(update query)이 일어나지 않음
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
