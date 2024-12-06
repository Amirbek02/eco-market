package com.example.eco_store;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.adapter.ProductAdapter;
import com.example.eco_store.module.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private Button cartButton;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
        cartButton = findViewById(R.id.cartButton);

        // Инициализация списка продуктов
        productList = new ArrayList<>();
        productList.add(new Product(1, "Яблоко красная радуга сладкая", 56, R.drawable.apple, 1));
        productList.add(new Product(2, "Драконий фрукт", 340, R.drawable.dracon, 1));

        // Установка адаптера
        ProductAdapter adapter = new ProductAdapter(this, productList, totalSum -> {
            // Обновление текста кнопки корзины
            cartButton.setText("Корзина: " + totalSum + " c");
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
