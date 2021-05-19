package jpabook.start.valueType.embedded.ex2;

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
            Address address = new Address("seoul" , "street" , "1000-2");

            Member member = new Member();
            member.setName("hello");
            member.setHomeAddress(address);
            em.persist(member);

            Address copyAddress = new Address("pusan" , "street" , "1000-2");

            Member member2 = new Member();
            member2.setName("hello 2");
//            member2.setHomeAddress(address);      // Embedded 타입은 공유되면 안됨
            member2.setHomeAddress(copyAddress);    // Embedded 타입인 address 를 복사하여야함
            em.persist(member2);

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
