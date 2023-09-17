package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * 값 타입은 Setter는 아예 제공을 안 해서 변경이 불가능하게 해야 한다.
 * JPA 기본 스펙이 리플렉션이나 프록시 기술을 써야하는데 기본 생성자가 없으면 그것들을 활용할 수 없으므로 꼭 기본생성자가 있어야 한다.
 * 그리고 public으로 기본 생성자를 정의하면 사람들이 너무 많이 접근할 수 있으므로 protected까지는 허용이 되는 것으로 정해졌다.
 *  - 아예 막아지는 것은 아니지만 건드리지 말자 하고 넘어갈 가능성이 높아지기도 하고 주석으로 설명을 추가해도 되기는 하다.
 */
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
