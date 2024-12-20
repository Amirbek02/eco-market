package com.example.eco_store.module;

public class History {
    private int id;
    private String productName;
    private String time;
    private String price;

    public History() {
        // Пустой конструктор для Firebase
    }

    public History(int id, String productName, String time, String price) {
        this.id = id;
        this.productName = productName;
        this.time = time;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
