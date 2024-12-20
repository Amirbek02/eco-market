package com.example.eco_store;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eco_store.R;
import com.example.eco_store.module.Order;
import com.example.eco_store.module.Product;

import java.util.List;

public class OpenHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_history);

        TextView textOrderId = findViewById(R.id.textOrderId);
        TextView textPhone = findViewById(R.id.textPhone);
        TextView textAddress = findViewById(R.id.textAddress);
        TextView textLandmark = findViewById(R.id.textLandmark);
        TextView textComments = findViewById(R.id.textComments);
        TextView textTotalSum = findViewById(R.id.textTotalSum);
        TextView textProducts = findViewById(R.id.textProducts);

        // Получение объекта заказа из Intent
        Order order = (Order) getIntent().getSerializableExtra("order");

        if (order != null) {
            textOrderId.setText("ID: " + order.getId());
            textPhone.setText("Телефон: " + order.getPhone());
            textAddress.setText("Адрес: " + order.getAddress());
            textLandmark.setText("Ориентир: " + order.getLandmark());
            textComments.setText("Комментарии: " + order.getComments());
            textTotalSum.setText("Сумма: " + order.getTotalSum() + " с");

            // Вывод списка продуктов
            StringBuilder productsList = new StringBuilder();
            List<Product> products = order.getProducts();
            if (products != null) {
                for (Product product : products) {
                    productsList.append(product.getProduct_name())
                            .append(" - ")
                            .append(product.getPrice())
                            .append(" с\n");
                }
            }
            textProducts.setText("Продукты:\n" + productsList.toString());
        }
    }
}
