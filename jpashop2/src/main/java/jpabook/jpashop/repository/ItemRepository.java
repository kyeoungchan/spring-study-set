package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {

        // 처음에 Item을 저장할 때는 id가 없어야 한다.
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
            // 이미 DB에 등록된 것을 가져오는 개념 -> update랑 비슷하다고 보면 된다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
