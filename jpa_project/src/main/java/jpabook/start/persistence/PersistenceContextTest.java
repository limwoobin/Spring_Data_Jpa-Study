package jpabook.start.persistence;

import jpabook.start.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContextTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            em.persist(member);

            // 1차 캐시에 저장이되어서 select query 가 안날라감
            Member findMember = em.find(Member.class , 101L);

            // 동일성 보장
            Member findMember1 = em.find(Member.class , 101L);
            Member findMember2 = em.find(Member.class , 101L);
            System.out.println("result = " + (findMember1 == findMember2));

            tx.commit();//트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }
}
