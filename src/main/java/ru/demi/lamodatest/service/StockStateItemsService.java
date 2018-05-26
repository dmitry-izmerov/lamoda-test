package ru.demi.lamodatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demi.lamodatest.entity.StockStateItem;
import ru.demi.lamodatest.repository.StockStateItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StockStateItemsService {

    private final StockStateItemRepository stockStateItemRepository;

    @Autowired
    public StockStateItemsService(StockStateItemRepository stockStateItemRepository) {
        this.stockStateItemRepository = stockStateItemRepository;
    }

    public void saveAll(List<StockStateItem> items) {
        stockStateItemRepository.saveAll(items);
    }

    public void deleteAll() {
        stockStateItemRepository.deleteAll();
    }
}
