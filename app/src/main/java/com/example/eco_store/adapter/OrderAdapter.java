package com.example.eco_store.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.R;
import com.example.eco_store.module.Product;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Product> productList;

    public OrderAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Product product = productList.get(position);
        int totalPrice = product.getPrice() * product.getSum();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTextView, productPriceTextView, productQuantityTextView;

        public OrderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
