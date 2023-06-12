package jpabook.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); // 셀프로 양방향 잡히게 하기

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"), // 내가 조인하는 건 얘고,
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID") // 반대쪽이 조인하는 건 얘다.
    )

    private List<Item> items = new ArrayList<>();

}
