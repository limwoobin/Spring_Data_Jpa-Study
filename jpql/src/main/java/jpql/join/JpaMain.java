package jpql.join;

import jpql.Member;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // inner
            String query = "select m from Member m inner join m.team t";
            List<Member> result = em.createQuery(query , Member.class)
                    .getResultList();

            // outer
            String query2 = "select m from Member m left join m.team t";
            List<Member> result2 = em.createQuery(query2 , Member.class)
                    .getResultList();

            // 세타조인
            String query3 = "select m from Member m, Team t where m.username = t.name";
            List<Member> result3 = em.createQuery(query3 , Member.class)
                    .getResultList();

            String query4 = "select m , t from Member m left join m.team t on t.name = 'A' ";
            List<Member> result4 = em.createQuery(query4 , Member.class)
                    .getResultList();

            String query5 = "select m from Member m left join Team t on m.username = t.name";
            List<Member> result5 = em.createQuery(query5 , Member.class)
                    .getResultList();

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
