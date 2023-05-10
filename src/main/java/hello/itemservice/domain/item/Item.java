package hello.itemservice.domain.item;

import lombok.Data;

@Data
// 사실 위험하다. Getter나 Setter만 사용하는 것이 좋다. 예측하지 못하게 동작할 수 있으므로. 여기서는 예제이므로 @Data 사용 (사용하기 전에 어떤 것들이 들어가서 동작하는지 다 보고 사용할 것)
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    // 값이 null이 들어가는 상황을 가정해서 Wrapper Class 사용

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
