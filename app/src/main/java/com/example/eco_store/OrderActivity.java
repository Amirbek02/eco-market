package com.example.eco_store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eco_store.module.Order;
import com.example.eco_store.module.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private EditText phoneEditText, addressEditText, landmarkEditText, commentsEditText;
    private Button orderButton;
    private TextView totalSumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Инициализация полей
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        landmarkEditText = findViewById(R.id.landmarkEditText);
        commentsEditText = findViewById(R.id.commentsEditText);
        totalSumTextView = findViewById(R.id.totalSumTextView);
        orderButton = findViewById(R.id.orderButton);

        // Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("orders");
        DatabaseReference counterRef = database.getReference("order_id_counter");
        Intent intent = getIntent();
        List<Product> receivedProductList = getIntent().getParcelableArrayListExtra("product_list");

        // Пример использования полученного списка для подсчета суммы заказа
        int totalSum = 0;
        for (Product product : receivedProductList) {
            Log.d("Product", "Name: " + product.getProduct_name());
            totalSum += product.getPrice() * product.getSum();
        }
        totalSumTextView.setText("Сумма заказа: " + totalSum + " с");

        // Обработчик кнопки
        int finalTotalSum = totalSum;
        orderButton.setOnClickListener(v -> {
            String phone = phoneEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String landmark = landmarkEditText.getText().toString();
            String comments = commentsEditText.getText().toString();

            if (phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Заполните все обязательные поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Получаем текущий ID и увеличиваем его
            counterRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Integer currentId = task.getResult().getValue(Integer.class);
                    if (currentId == null) {
                        currentId = 0;  // Если счетчик еще не был установлен, начинаем с 0
                    }

                    // Увеличиваем ID на 1
                    int newId = currentId + 1;

                    // Создание объекта заказа с новым ID
                    Order order = new Order(String.valueOf(newId), phone, address, landmark, comments, finalTotalSum, receivedProductList);

                    // Сохранение нового заказа в Firebase
                    ordersRef.child(String.valueOf(newId)).setValue(order).addOnCompleteListener(orderTask -> {
                        if (orderTask.isSuccessful()) {
                            // Обновляем счетчик ID в Firebase
                            counterRef.setValue(newId);

                            Toast.makeText(this, "Заказ оформлен успешно!", Toast.LENGTH_SHORT).show();
                            finish(); // Закрыть экран
                        } else {
                            Toast.makeText(this, "Ошибка при оформлении заказа", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Ошибка при получении счетчика ID", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    // Класс для хранения данных заказа
}
