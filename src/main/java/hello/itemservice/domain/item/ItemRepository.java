package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; // static 사용. 스프링 싱글톤 사용시 사실 꼭 static이 필수는 아니지만 따로 new를 쓰면 과도하게 생성이될 수 있으므로 사용하는 것이 좋다.

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); // ArrayList로 감싸서 반환해야 store 변경이 외부에서 일어나지 않는다.
    }

    public void update(Long itemId, Item updateParam) {

        /* 사실은 ItemDto 같이 id랑 별개로 itemName, price, quantity를 다루는 클래스를 새로 만드는 것이 맞다.
         * 개발자가 setId 등 사용할 수 있지 않나하고 혼란스러워할 수 있기 때문이다.
         * 여기서는 프로젝트가 작아서 괜찮지만 프로젝트 규모가 커지면 ItemParameterDto 같이 명확하게 새롭게 만드는 것이 낫다.
         * 중복이냐 명확성이냐 하면 명확성을 따르는 것이 낫다.*/

        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
