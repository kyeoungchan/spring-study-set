package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) // 중간 테이블을 새로 매핑을 따로 헤줘야 한다. 관계형 데이터베이스는 컬렉션 컬렉션 매핑 개념이 없으므로.
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    // 나의 부모는 나고, 부모는 하나다.
    // 이름만 같지 다른 엔티티랑 매핑한다고 생각하면 된다.

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
