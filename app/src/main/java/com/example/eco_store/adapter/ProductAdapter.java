package com.example.eco_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.R;
import com.example.eco_store.module.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private final Context context;
    private final OnCartUpdateListener listener;

    // Интерфейс для обновления корзины
    public interface OnCartUpdateListener {
        void onCartUpdated(int totalSum);
    }

    public ProductAdapter(Context context, List<Product> productList, OnCartUpdateListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Устанавливаем данные в элементы
        holder.productName.setText(product.getProduct_name());
        holder.productPrice.setText(product.getPrice() + " c");
        holder.productImage.setImageResource(product.getProduct_image());
        holder.productQuantity.setText(String.valueOf(product.getSum()));

        // Логика изменения количества
        holder.increaseButton.setOnClickListener(v -> {
            product.setSum(product.getSum() + 1);
            notifyItemChanged(position);
            updateCartSum();
        });

        holder.decreaseButton.setOnClickListener(v -> {
            if (product.getSum() > 0) {
                product.setSum(product.getSum() - 1);
                notifyItemChanged(position);
                updateCartSum();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Подсчёт суммы корзины
    private void updateCartSum() {
        int totalSum = 0;
        for (Product product : productList) {
            totalSum += product.getPrice() * product.getSum();
        }
        listener.onCartUpdated(totalSum);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        Button increaseButton, decreaseButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
        }
    }
}
