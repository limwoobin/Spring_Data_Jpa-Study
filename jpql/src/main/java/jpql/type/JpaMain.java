package jpql.type;


import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            Member2 member = new Member2();
            member.setUsername("memberA");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            String query = "select m.username, 'HELLO' , true From Member2 m " +
                    "where m.type = jpql.type.MemberType.ADMIN";
            List<Object[]> result = em.createQuery(query)
                    .getResultList();

            String query2 = "select m.username, 'HELLO' , true From Member2 m " +
                    "where m.type = :memberType";
            List<Object[]> result2 = em.createQuery(query2)
                    .setParameter("memberType" , MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
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
