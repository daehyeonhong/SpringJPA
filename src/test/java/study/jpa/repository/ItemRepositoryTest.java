package study.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.jpa.entity.Item;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName(value = "아이템 저장")
    public void save() {
        //given
        final Item item = new Item("AAA");
        this.itemRepository.save(item);
        //when

        //then
    }
}
