package com.example.eco_store.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.R;
import com.example.eco_store.adapter.HistoryAdapter;
import com.example.eco_store.module.History;
import com.example.eco_store.module.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private Button cartButton;
    private List<Order> historyList;  // Используем Order, а не History

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHistory);

        // Инициализация данных истории
        historyList = new ArrayList<>();

        // Получение данных из Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                historyList.clear();  // Очищаем список перед добавлением новых данных

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);  // Преобразуем данные в объект Order

                    if (order != null) {
                        // Логируем данные для проверки
                        Log.d("HistoryFragment", "Order ID: " + order.getId());
                        Log.d("HistoryFragment", "Address: " + order.getAddress());

                        historyList.add(order);  // Добавляем заказ в список
                    }
                }

                // Установка адаптера
                HistoryAdapter adapter = new HistoryAdapter(getContext(), historyList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));  // Используем getContext()
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "loadPost:onCancelled", databaseError.toException());
            }
        });

        return view;
    }
}
