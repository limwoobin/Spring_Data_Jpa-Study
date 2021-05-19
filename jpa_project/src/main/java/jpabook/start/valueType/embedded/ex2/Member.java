package jpabook.start.valueType.embedded.ex2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;
}
