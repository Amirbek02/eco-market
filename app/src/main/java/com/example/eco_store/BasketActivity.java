package com.example.eco_store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.adapter.BasketAdapter;
import com.example.eco_store.module.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {

    private BasketAdapter basketAdapter;
    private RecyclerView recyclerViewProducts;
    private List<Product> productList;
    private TextView basketSum;
    private Button cartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        // Инициализация элементов
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        basketSum = findViewById(R.id.basket_sum);
        cartButton = findViewById(R.id.cartButton);

        productList = new ArrayList<>();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("countries");

        basketAdapter = new BasketAdapter(this, productList);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProducts.setAdapter(basketAdapter);
        if (productList.isEmpty()) {
            // Список пуст
            Log.d("BasketActivity", "Список товаров пуст");
        } else {
            // Список содержит элементы
            Log.d("BasketActivity", "Список товаров содержит " + productList.size() + " элементов");
        }

        // Слушатель обновления корзины
        basketAdapter.setOnCartUpdatedListener(totalSum -> basketSum.setText(totalSum + " c"));

        // Обработчик кнопки "Оплатить"


        // Загрузка данных из Firebase
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> newProductList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue(String.class);
                    String productName = snapshot.child("product_name").getValue(String.class);
                    int price = snapshot.child("price").getValue(Integer.class);
                    int productImage = snapshot.child("product_image").getValue(Integer.class);
                    int sum = snapshot.child("sum").getValue(Integer.class);
                    int category = snapshot.child("category").getValue((Integer.class));

                    Product product = new Product(id, productName, price,productImage, sum, category);
                    newProductList.add(product);
                    productList.add(product);
                }
                cartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BasketActivity.this, OrderActivity.class);
                        intent.putParcelableArrayListExtra("product_list", new ArrayList<>(productList));

                        startActivity(intent);
                    }
                });
                basketAdapter.updateProducts(newProductList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Ошибка: " + databaseError.getMessage());
                Toast.makeText(BasketActivity.this, "Не удалось загрузить данные", Toast.LENGTH_SHORT).show();
            }
        });
    }
}