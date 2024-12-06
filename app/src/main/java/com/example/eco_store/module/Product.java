package com.example.eco_store.module;

public class Product {
    private int id;
    private String product_name;
    private int price;
    private int product_image;
    private int sum;

    public Product(int id, String product_name, int price, int product_image, int sum) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.product_image = product_image;
        this.sum = sum;
    }

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
