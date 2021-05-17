package jpabook.start.ex13;

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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

//            em.persist(child1);
//            em.persist(child2);
//            parent 의 cascade 가 ALL 이면 위 코드 없이 child 도 영속성이 전이되어 저장된다


            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class , parent.getId());
            findParent.getChildList().remove(0);
            // 고아객체 orphanRemoval -> 부모객체와의 연관관계가 끊어지면 자동으로 삭제

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
