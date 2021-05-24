package jpql.func;


import jpql.Member;
import jpql.type.Member2;
import jpql.type.MemberType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(10);

            em.flush();
            em.clear();

            String query = "select concat('a' , 'b') From Member m";
            List<String> result = em.createQuery(query , String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }

            String query2 = "select substring(m.username , 2, 3) From Member m";
            List<String> result2 = em.createQuery(query2 , String.class)
                    .getResultList();

            for (String s : result2) {
                System.out.println("s = " + s);
            }

            String query3 = "select locate('de' , 'abcdefg') From Member m";
            List<Integer> result3 = em.createQuery(query3 , Integer.class)
                    .getResultList();

            for (Integer s : result3) {
                System.out.println("s = " + s);
            }

            String query4 = "select size(t.members) From Team t";
            List<Integer> result4 = em.createQuery(query4 , Integer.class)
                    .getResultList();

            for (Integer s : result4) {
                System.out.println("s = " + s);
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
