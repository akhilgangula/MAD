package com.example.inclass05grp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AppCategoryFragment.IAppCategories, TopPaidAppsFragment.ITopPaidApps {

    String FRAGMENT_TAG = "fragment_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container_view, new AppCategoryFragment(), FRAGMENT_TAG).commit();
    }

    @Override
    public void selectedCategory(String category) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, TopPaidAppsFragment.newInstance(category), FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void onSelectApp(DataServices.App app) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, AppDetailFragment.newInstance(app), FRAGMENT_TAG)
                .addToBackStack(null).commit();
    }
}