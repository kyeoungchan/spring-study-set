package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 리포지토리에서 컨트롤러로 의존관계가 들어가는 이상한 사태를 막기 위해서 repository 패키지에 별도로 DTO 클래스 선언
 */
@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

//    public OrderSimpleQueryDto(Order order) {
    // DTO는 엔티티를 참조해도 괜찮다. 그러나 JPA에서는 jpql 내부에서는 엔티티를 식별자로 넣어버리기 때문에 파라미터로 엔티티를 넣으면 안 된다.

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {

        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address; // 엔티티는 안 되지만, 값 타입은 가능하다.
    }
}