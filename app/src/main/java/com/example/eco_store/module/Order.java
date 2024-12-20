package com.example.eco_store.module;

import java.util.List;

public class Order {
    private String id;
    private String phone;
    private String address;
    private String landmark;
    private String comments;
    private int totalSum;
    private List<Product> products;

    public Order() {
        // Пустой конструктор для Firebase
    }

    public Order(String id, String phone, String address, String landmark, String comments, int totalSum, List<Product> products) {
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.landmark = landmark;
        this.comments = comments;
        this.totalSum = totalSum;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Геттеры и сеттеры
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
