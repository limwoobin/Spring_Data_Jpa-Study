package jpql.projections;

import jpql.Address;
import jpql.Member;
import jpql.Team;
import jpql.dto.MemberDTO;

import javax.persistence.*;
import java.util.List;

public class ProjectionMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Member> result = em.createQuery("select m from Member m" , Member.class)
                    .getResultList();   // 이렇게 불러와도 영속성 컨텍스트에 의해 관리된다

            Member findMember = result.get(0);
            findMember.setAge(20);

            em.flush();
            em.clear();

            // 엔티티 타입 프로젝션
            Team team = em.createQuery("select m.team from Member m join m.team" , Team.class)
                    .getSingleResult();

            // 임베디드 타입 프로젝션
            List<Address> addressResult = em.createQuery("select o.address from Order o" , Address.class)
                    .getResultList();

            // 스칼라 타입 프로젝션
            // 첫번째 방법
            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] objectResult = (Object[]) o;
            System.out.println("objectResult[0] = " + objectResult[0]);
            System.out.println("objectResult[1] = " + objectResult[1]);

            // 두번째 방법
            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            Object[] objectResult2 = resultList2.get(0);
            System.out.println("objectResult2[0] = " + objectResult2[0]);
            System.out.println("objectResult2[1] = " + objectResult2[1]);


            // 세번째 방법 new 명령어로 조회
            // 생성자 순서ㅗ아 타입이 일치해야함
            List<MemberDTO> memberDTOS = em.createQuery("select new jpql.dto.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            
            MemberDTO memberDTO = memberDTOS.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

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
