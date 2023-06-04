package jpabasic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Member {

    @Id
    private Long id;
    private String name;
    private int age;

    public Member() {
    }

    public Member(Long id, String name) {
        /* JPA는 내부적으로 리플렉션이 있기 때문에 동적으로 객체를 생성해야 한다.
         * 따라서 기본 생성자가 필요하다.*/
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
