package ru.demi.lamodatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.demi.lamodatest.entity.StockStateItem;

@Repository
public interface StockStateItemRepository extends JpaRepository<StockStateItem, Long> {
}
