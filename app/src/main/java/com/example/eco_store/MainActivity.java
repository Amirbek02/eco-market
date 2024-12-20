package com.example.eco_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.eco_store.Fragment.HistoryFragment;
import com.example.eco_store.Fragment.InfoFragment;
import com.example.eco_store.Fragment.MainFragment;
import com.example.eco_store.Fragment.ProductFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(new MainFragment());
        tabLayout = findViewById(R.id.tabLayout);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setFragment(new MainFragment());
                        break;
                    case 1:
                        setFragment(new ProductFragment());
                        break;
                    case 2:
                        setFragment(new HistoryFragment());
                        break;
                    case 3:
                        setFragment(new InfoFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment).commit();
    }
}

