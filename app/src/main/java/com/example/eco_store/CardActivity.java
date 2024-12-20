package com.example.eco_store;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    private TextView productName, productPrice, productQuantity;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productQuantity = findViewById(R.id.productQuantity);
        productImage = findViewById(R.id.productImage);

        // Получаем данные из Intent
        String name = getIntent().getStringExtra("product_name");
        int price = getIntent().getIntExtra("product_price", 0);
        int imageResId = getIntent().getIntExtra("product_image", R.drawable.apple);  // Укажите дефолтное изображение
        int quantity = getIntent().getIntExtra("product_quantity", 0);

        // Устанавливаем данные в элементы
        productName.setText(name);
        productPrice.setText(price + " c");
        productQuantity.setText(String.valueOf(quantity));
        productImage.setImageResource(imageResId);
    }
}
