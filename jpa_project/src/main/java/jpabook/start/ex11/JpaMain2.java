package jpabook.start.ex11;

import jpabook.start.ex10.Member;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {

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
            System.out.println("isLoaded=" + emf.getPersistenceUnitUtil().isLoaded(refMember)); // 초기화되지 않았으니 false

            refMember.getName();    // proxy 객체 초기화
            System.out.println("isLoaded=" + emf.getPersistenceUnitUtil().isLoaded(refMember)); // 초기화되어서 로드되었으니 true

            Hibernate.initialize(refMember);    // 강제 초기화 (Hibernate 가 제공 , Jpa 표준에는 없음)

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
