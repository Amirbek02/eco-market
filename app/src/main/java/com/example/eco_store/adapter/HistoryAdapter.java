package com.example.eco_store.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.OpenHistoryActivity;
import com.example.eco_store.R;
import com.example.eco_store.module.History;
import com.example.eco_store.module.Order;
import com.example.eco_store.module.Product;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<Order> historyList;

    public HistoryAdapter(Context context, List<Order> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = historyList.get(position);

        holder.textTitle.setText("Заказ: " + order.getId());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OpenHistoryActivity.class);
            intent.putExtra("order", (Parcelable) order);  // Передаем объект заказа
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textTime, textPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textTime = itemView.findViewById(R.id.textTime);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }
}

