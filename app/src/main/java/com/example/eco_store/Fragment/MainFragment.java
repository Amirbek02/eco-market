package com.example.eco_store.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eco_store.R;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Инфлейт разметку фрагмента
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.category1).setOnClickListener(this::onClick);
        view.findViewById(R.id.category2).setOnClickListener(this::onClick);
        view.findViewById(R.id.category3).setOnClickListener(this::onClick);
        view.findViewById(R.id.category4).setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        ProductFragment productFragment = new ProductFragment();

        // Определите категорию и её ID
        if (view.getId() == R.id.category1) {
            bundle.putInt("categoryId", 1); // ID категории 1 (Фрукты)
            bundle.putString("categoryName", "Фрукты");
        } else if (view.getId() == R.id.category2) {
            bundle.putInt("categoryId", 2); // ID категории 2 (Сухофрукты)
            bundle.putString("categoryName", "Сухофрукты");
        } else if (view.getId() == R.id.category3) {
            bundle.putInt("categoryId", 3); // ID категории 3 (Овощи)
            bundle.putString("categoryName", "Овощи");
        } else if (view.getId() == R.id.category4) {
            bundle.putInt("categoryId", 4); // ID категории 4 (Зелень)
            bundle.putString("categoryName", "Зелень");
        }

        productFragment.setArguments(bundle);

        // Переход на новый фрагмент с передачей данных
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, productFragment)
                .addToBackStack(null)
                .commit();
    }


}

