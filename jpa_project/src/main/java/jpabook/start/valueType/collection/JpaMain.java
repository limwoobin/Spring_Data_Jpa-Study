package jpabook.start.valueType.collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("myCity" , "street" , "100-15"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("햄버거");

            member.getAddressHistory().add(new Address("old1" , "old_street" , "old_100-15"));
            member.getAddressHistory().add(new Address("old2" , "old2_street" , "old2_100-15"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=============== START =============");
            Member findMember = em.find(Member.class , member.getId());

            List<Address> addressList = findMember.getAddressHistory();
            for (Address address : addressList) {
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String food : favoriteFoods) {
                System.out.println("food = " + food);
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
