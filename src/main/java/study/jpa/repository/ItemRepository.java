package study.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.jpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
