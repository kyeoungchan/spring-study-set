package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "/basic/item";
    }

    /**
     * @ModelAttribute("item") Item item
     * model.addAttribute("item", item); 자동 추가 생략 가능
     * @ModelAttribute()에 있는 "item"이 그대로 매핑돼서 템플릿 엔진에 전달되므로 "item"을 바꾸면 안 된다.
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {

        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "/basic/item";
    }

    /**
     * @ModelAttribute("item") Item itemValue
     * @ModelAttribute의 파라미터로 들어가는 name 속성의 값은 뷰 템플릿 엔진에서 사용이 되는 name이고,
     * Item 타입의 변수명인 itemValue는 자바 코드 메서드에서 사용되는 name이다.
     */
//    @PostMapping("/add")
    public String addItemV2_2(@ModelAttribute("item") Item itemValue) {

        itemRepository.save(itemValue);
//        model.addAttribute("item", item);

        return "/basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {

//        Item -> item로 바껴서 ModelAttribute에 담긴다.
        itemRepository.save(item);
//        model.addAttribute("item", item);

        return "/basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "/basic/item";
    }

    /**
     * PRG - Post/Redirect/Get
     */
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * RedirectAttributes
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; // redirectAttributes에 들어간 itemId가 reidrect 경로에 매핑되고, status처럼 남는 애들은 쿼리 파라미터로 들어가게 된다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, Model model) {

        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    //    @PostMapping("/{itemId}/edit")
    public String edit2(@PathVariable Long itemId, @ModelAttribute Item item, Model model) {

//        itemRepository.update(itemId, item);
//        return "redirect:/basic/items/{itemId}";
        /* 만약 리다이렉트로 하지 않고 뷰 호출만 한다면!
         * 컨트롤러 호출이 아니라 뷰 호출을 하게 되는데 그러려면 itemId를 모델에 담고 basic/item을 호출해야하는데
         * 그게 결국 item() 메서드랑 로직이 같고 중복이다.
         * 그리고 Post 메서드로 매핑이 계속 되는 것이기 때문에 멱등성이 보장되지 않아 위험할 수 있다.
         * 따라서 그냥 리다이렉트로 내보내는 것이 낫다.*/

        itemRepository.update(itemId, item);
        Item updatedItem = itemRepository.findById(itemId);
        model.addAttribute("item", updatedItem);
        return "basic/item";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
