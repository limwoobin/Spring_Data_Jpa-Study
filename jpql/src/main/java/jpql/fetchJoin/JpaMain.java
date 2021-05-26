package jpql.fetchJoin;


import jpql.Member;
import jpql.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            Team team1 = new Team();
            team1.setName("TeamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("TeamA");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("memberA");
            member1.setAge(10);
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("memberB");
            member2.setAge(25);
            member1.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("memberC");
            member3.setAge(22);
            member3.setTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();

            // fetch join (Many To One)
            String query = "select m From Member m join fetch m.team";
            List<Collection> result = em.createQuery(query , Collection.class)
                    .getResultList();

            System.out.println("result = " + result);


            String query2 = "select t From Team t join fetch t.members";
            List<Team> result2 = em.createQuery(query2 , Team.class)
                    .getResultList();

            for (Team t : result2) {
                System.out.println("t = " + t);
            }

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
