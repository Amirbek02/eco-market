package com.example.eco_store.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.R;
import com.example.eco_store.module.Product;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnCartUpdatedListener listener;

    public BasketAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void setOnCartUpdatedListener(OnCartUpdatedListener listener) {
        this.listener = listener;
    }

    public interface OnCartUpdatedListener {
        void onCartUpdated(int totalSum);
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_basket, parent, false);
        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        Product product = productList.get(position);

        // Установка данных в виджеты
        holder.productName.setText(product.getProduct_name());
        holder.productPrice.setText(product.getPrice() + " c");
        holder.productQuantity.setText(String.valueOf(product.getSum()));

        // Обработка кнопки "Увеличить"
        holder.increaseButton.setOnClickListener(v -> {
            product.setSum(product.getSum() + 1);
            holder.productQuantity.setText(String.valueOf(product.getSum()));
            notifyItemChanged(position);
            updateTotalSum();
        });

        holder.decreaseButton.setOnClickListener(v -> {
            if (product.getSum() > 1) {
                product.setSum(product.getSum() - 1);
                holder.productQuantity.setText(String.valueOf(product.getSum()));
                notifyItemChanged(position);
                updateTotalSum();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Обновление общей суммы товаров
    private void updateTotalSum() {
        if (listener != null) {
            int totalSum = 0;
            for (Product product : productList) {
                totalSum += product.getPrice() * product.getSum();
            }
            listener.onCartUpdated(totalSum);
        }
    }

    // Метод для обновления данных в адаптере
    public void updateProducts(List<Product> newProductList) {
        productList.clear(); // Очищаем текущий список
        productList.addAll(newProductList); // Добавляем новые данные
        notifyDataSetChanged(); // Уведомляем адаптер об изменениях
    }

    public static class BasketViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity;
        ImageView productImage;
        AppCompatButton decreaseButton, increaseButton;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.basketName);
            productPrice = itemView.findViewById(R.id.basketPrice);
            productQuantity = itemView.findViewById(R.id.basketQuantity);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
        }
    }
}
