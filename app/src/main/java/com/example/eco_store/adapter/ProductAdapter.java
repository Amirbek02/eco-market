package com.example.eco_store.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.CardActivity;
import com.example.eco_store.R;
import com.example.eco_store.module.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private static List<Product> filteredProductList;
    private final Context context;
    private final OnCartUpdateListener listener;

    // Интерфейс для обновления корзины
    public interface OnCartUpdateListener {
        void onCartUpdated(int totalSum);
    }

    public ProductAdapter(Context context, List<Product> filteredProductList, OnCartUpdateListener listener) {
        this.context = context;
        this.filteredProductList = filteredProductList;
        this.listener = listener;
    };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = filteredProductList.get(position);

        // Устанавливаем данные в элементы
        holder.productName.setText(product.getProduct_name());
        holder.productPrice.setText(product.getPrice() + " c");
        holder.productImage.setImageResource(product.getProduct_image());
        holder.productQuantity.setText(String.valueOf(product.getSum()));
        holder.addButton.setOnClickListener(v -> {
            holder.quantityControlLayout.setVisibility(View.VISIBLE);  // Показываем элементы управления
            holder.addButton.setVisibility(View.GONE);  // Скрываем кнопку "Добавить"
        });

        holder.itemView.setOnClickListener(v -> {
            // Создаём Intent для перехода на ProductPageActivity
            Intent intent = new Intent(context, CardActivity.class);
            intent.putExtra("product_name", product.getProduct_name());
            intent.putExtra("product_price", product.getPrice());
            intent.putExtra("product_image", product.getProduct_image());
            intent.putExtra("product_quantity", product.getSum());
            context.startActivity(intent);
        });

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
        return filteredProductList.size();
    }

    // Подсчёт суммы корзины
    private void updateCartSum() {
        int totalSum = 0;
        for (Product product : filteredProductList) {
            totalSum += product.getPrice() * product.getSum();
        }
        listener.onCartUpdated(totalSum);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        Button addButton;
        LinearLayout quantityControlLayout;
        Button increaseButton, decreaseButton;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            addButton = itemView.findViewById(R.id.addButton);
            quantityControlLayout = itemView.findViewById(R.id.quantityControlLayout);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
        }
    }
}
