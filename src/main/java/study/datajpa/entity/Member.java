package study.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

    protected Member() {
        // JPA 가 프록시 기술을 사용할 수 있게 하기 위해서 기본 생성자 필요
        // private 으로 하면 막힐 수 있으나, protected 로 설정하면 프록시 생성 가능
    }

    public Member(String username) {
        this.username = username;
    }
}
