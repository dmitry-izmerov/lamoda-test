package ru.demi.lamodatest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.demi.lamodatest.entity.StockStateItem;

@Repository
public interface StockStateItemRepository extends CrudRepository<StockStateItem, Long> {
}
