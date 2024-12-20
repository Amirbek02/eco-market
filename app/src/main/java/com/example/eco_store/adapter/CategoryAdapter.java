package com.example.eco_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.Fragment.ProductFragment;
import com.example.eco_store.R;
import com.example.eco_store.module.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View caregoryItems = LayoutInflater.from(context).inflate(R.layout.category_items, parent, false);

        return new CategoryViewHolder(caregoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int categoryId = categories.get(position).getId();
                if (categoryId == 0) {
                    // Если выбрана категория "Все", показываем все продукты
                    ProductFragment.showAllProducts();
                } else {
                    // Иначе фильтруем по выбранной категории
                    ProductFragment.showProductsByCategory(categoryId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static final class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
        }
    }

}
