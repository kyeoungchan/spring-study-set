package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // 내장 타입을 포함했다는 어노테이션. @Embeddable과 더불어 둘 중 하나만 있어도 된다.
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    /* Order 테이블에 있는 "member" 필드에 의해서 매핑됐다는 의미로 보면 된다.
     * 나는 매핑을 하는 애가 아니라 누군가에 의해서 mapped된 거울일 뿐이라고 표현하는 것
     * 따라서 여기에 값을 넣는다고 해서 FK 값이 변경되지 않는다.*/
    private List<Order> orders = new ArrayList<>();
}
