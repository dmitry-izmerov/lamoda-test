package ru.demi.lamodatest.xml;

public class LoadingStockStateItem {
    private int id;
    private String sku;
    private int count;

    public LoadingStockStateItem() {
    }

    public LoadingStockStateItem(int id, String sku, int count) {
        this.id = id;
        this.sku = sku;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return "LoadingStockStateItem{" +
            "id=" + id +
            ", sku='" + sku + '\'' +
            ", count=" + count +
            '}';
    }
}
