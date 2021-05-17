package jpabook.start.ex12;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩
//    @ManyToOne(fetch = FetchType.EAGER) // 즉시로딩
    @JoinColumn(name = "TEAM_ID")
    private Team team;
}

