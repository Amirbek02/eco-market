package com.example.eco_store.module;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String id;
    private String product_name;
    private int price;
    private int product_image;
    private int sum;
    private int category;

    // Пустой конструктор для Firebase
    public Product() {
    }

    // Конструктор с параметрами
    public Product(String id, String product_name, int price, int product_image, int sum,  int category) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.product_image = product_image;
        this.sum = sum;
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    // Реализация Parcelable
    protected Product(Parcel in) {
        id = in.readString();
        product_name = in.readString();
        price = in.readInt();
        product_image = in.readInt();
        sum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(product_name);
        dest.writeInt(price);
        dest.writeInt(product_image);
        dest.writeInt(sum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
