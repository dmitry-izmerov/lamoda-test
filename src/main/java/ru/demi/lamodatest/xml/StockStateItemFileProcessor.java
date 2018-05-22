package ru.demi.lamodatest.xml;

public class StockStateItemFileProcessor implements StockStateItemProcessor {
    @Override
    public void process(LoadingStockStateItem item) {
        System.out.println(item.toString());
    }
}
