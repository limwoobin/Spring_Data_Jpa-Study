package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작

//            save(em);
//            find(em);
//            remove(em);
            update(em);

            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    // 등록
    public static void save(EntityManager em) {
        String id = "id2";
        Member member = new Member();
        member.setId(id);
        member.setName("ZZZ");

        em.persist(member);
    }

    // 조회
    public static void find(EntityManager em) {
        String id = "id1";
        Member findMember = em.find(Member.class , id);
        System.out.println("id: " + findMember.getId());
        System.out.println("name: " + findMember.getName());
    }

    // 삭제
    public static void remove(EntityManager em) {
        String id = "id1";
        Member findMember = em.find(Member.class , id);
        em.remove(findMember);
    }

    // 수정
    public static void update(EntityManager em) {
        String id = "id1";
        Member findMember = em.find(Member.class , id);
        findMember.setName("updated Name");
    }
}
