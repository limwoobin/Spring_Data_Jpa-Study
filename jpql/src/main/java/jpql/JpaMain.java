package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m" , Member.class);
            TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.id = 10" , Member.class);

            List<Member> members = query1.getResultList();
            Member findMember = query2.getSingleResult();

            Member singleResult = em.createQuery("select m from Member m where m.username=:username" , Member.class)
                .setParameter("username" , "member1")
                .getSingleResult();

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
