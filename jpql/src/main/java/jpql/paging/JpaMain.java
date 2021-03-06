package jpql.paging;

import jpql.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            for (int i=0; i<100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }
            
            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m order by m.age desc" , Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();

            System.out.println("result.size() = " + result.size());
            for (Member member : result) {
                System.out.println("member.getId() = " + member.getId());
                System.out.println("member.getUsername() = " + member.getUsername());
                System.out.println("member.getAge() = " + member.getAge());
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
