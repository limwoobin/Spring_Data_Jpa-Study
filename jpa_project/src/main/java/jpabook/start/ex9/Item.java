package jpabook.start.ex9;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일테이블
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  // 각 테이블별로 컬럼생성
@DiscriminatorColumn
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;
}
