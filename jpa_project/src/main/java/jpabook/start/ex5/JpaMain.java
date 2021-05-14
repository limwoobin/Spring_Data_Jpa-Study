package jpabook.start.ex5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 연관관계의 주인이 member 의 team 이기 때문에 정상적으로 들어간다
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);   // <-- 연관관계의 주인인 member 의 team 에서 연관관계 설정
            em.persist(member);

            // 연관관계의 주인이 아닌 team 의 member 에서 연관관계를 설정했기 때문에 정상적으로 들어가지 않는다
            Member member2 = new Member();
            member.setUsername("member2");
            em.persist(member2);

            Team team2 = new Team();
            team.setName("TeamB");
            team.getMembers().add(member2); // <-- 연관관계를 잘못설정한 부분
            em.persist(team2);


            Team myTeam = new Team();
            team.setName("myTeam");
            em.persist(myTeam);

            Member myMember = new Member();
            myMember.setUsername("MyMember");
            myMember.setTeam(myTeam);    // <-- 연관관계 편의 메서드 작성

            Team newTeam = new Team();
            newTeam.setName("newTeam");
            em.persist(newTeam);

            Member newMember = new Member();
            newMember.setUsername("new Member");
            em.persist(newMember);

            newTeam.addMember(newMember);

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
