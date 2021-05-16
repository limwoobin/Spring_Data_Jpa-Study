package jpabook.start.ex9;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
//@DiscriminatorValue(value = "A")
// DType 값 설정 default 는 테이블 네임
public class Album extends Item {
    private String artist;
}
