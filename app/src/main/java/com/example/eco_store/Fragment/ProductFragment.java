package com.example.eco_store.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eco_store.BasketActivity;
import com.example.eco_store.R;
import com.example.eco_store.adapter.CategoryAdapter;
import com.example.eco_store.adapter.ProductAdapter;
import com.example.eco_store.module.Category;
import com.example.eco_store.module.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ProductFragment extends Fragment {
    RecyclerView categoryRecycler;
    CategoryAdapter categoryAdapter;
    private Button cartButton;
    private static List<Product> productList;
    static List<Product> fullproductList = new ArrayList<>();
    private static List<Product> filteredProductList;  // New filtered list for search
    ImageView basket;
    private int categoryId;
    private String categoryName;
    private static ProductAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProducts);
        RecyclerView recyclerView2 = view.findViewById(R.id.categoryRecycler);
        cartButton = view.findViewById(R.id.cartButton);
        basket = view.findViewById(R.id.basket);
        EditText searchField = view.findViewById(R.id.searchField);
//        if (getArguments() != null) {
//            categoryId = getArguments().getInt("categoryId");
//            categoryName = getArguments().getString("categoryName");
//        }
//
//        TextView categoryTitle = view.findViewById(R.id.categoryTitle);
//
//        categoryTitle.setText(categoryName);
//        showProductsByCategory(categoryId);
//        Log.d("MainFragment", "categoryId: " + categoryId);
//        Log.d("MainFragment", "categoryName: " + categoryName);


        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BasketActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the product list and filtered list
        productList = new ArrayList<>();
        filteredProductList = new ArrayList<>();

        // Populate the product list with products
        productList.add(new Product("1", "Яблоко красная радуга сладкая", 56, R.drawable.apple, 0, 2));
        productList.add(new Product("2", "Драконий фрукт", 340, R.drawable.dracon, 0, 3));
        productList.add(new Product("3", "Яблоки Ренет Симиренко", 130, R.drawable.simapple, 0, 4));
        productList.add(new Product("4", "Апельсины сладкий пакистанский", 86, R.drawable.orange, 0,1));
        productList.add(new Product("5", "Апельсины сладкий пакистанский", 86, R.drawable.orange, 0,6));
        productList.add(new Product("6", "Апельсины сладкий пакистанский", 86, R.drawable.orange, 0,1));
        productList.add(new Product("7", "Апельсины сладкий пакистанский", 86, R.drawable.orange, 0,5));

        // Set filtered list to product list initially
        filteredProductList.addAll(productList);
        fullproductList.addAll(productList);

        // Initialize adapter with the filtered list
        adapter = new ProductAdapter(getContext(), filteredProductList, totalSum -> {
            cartButton.setText("Корзина: " + totalSum + " c");
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns
        recyclerView.setAdapter(adapter);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filter products based on search input
                filterProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(0, "Все"));
        categoryList.add(new Category(1, "Фрукты"));
        categoryList.add(new Category(2, "Сухофрукты"));
        categoryList.add(new Category(3, "Овощи"));
        categoryList.add(new Category(4, "Зелень"));
        categoryList.add(new Category(5, "Чай кофе"));
        categoryList.add(new Category(6, "Молочные продукты"));

        CategoryAdapter adapter2 = new CategoryAdapter(getContext(), categoryList);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(adapter2);


        cartButton.setOnClickListener(v -> {
            for (Product selectedProduct : productList) {
                if (selectedProduct.getSum() > 0) {
                    Product newProduct = new Product(
                            selectedProduct.getId(),  // Используем текущий id
                            selectedProduct.getProduct_name(),
                            selectedProduct.getPrice(),
                            selectedProduct.getProduct_image(),
                            selectedProduct.getSum(),
                            selectedProduct.getCategory()
                    );
                    addProductToFirebase(newProduct);
                }
            }
        });

        return view;
    }
    private void addProductToFirebase(Product product) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("countries");

        String key = productRef.push().getKey();

        if (key != null) {
            productRef.child(key).setValue(product)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Продукт успешно добавлен в Firebase", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Ошибка при добавлении в Firebase", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Ошибка: Не удалось получить уникальный ключ", Toast.LENGTH_SHORT).show();
        }
    }
    private void setCategoryRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        categoryRecycler = getView().findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void showProductsByCategory(int category) {
        List<Product> filteredList = new ArrayList<>();
        for (Product p : fullproductList) {
            if (p.getCategory() == category) {
                filteredList.add(p);
            }
        }
        filteredProductList.clear();
        filteredProductList.addAll(filteredList);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
    public static void showAllProducts() {
        filteredProductList.clear();
        filteredProductList.addAll(fullproductList);  // Добавляем все продукты из полного списка

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }



    private void filterProducts(String query) {
        filteredProductList.clear();

        if (query.isEmpty()) {
            filteredProductList.addAll(productList);  // Show all products if query is empty
        } else {
            for (Product product : productList) {
                if (product.getProduct_name().toLowerCase().contains(query.toLowerCase())) {
                    filteredProductList.add(product);
                }
            }
        }

        adapter.notifyDataSetChanged();  // Notify the adapter to update the UI
    }
}
