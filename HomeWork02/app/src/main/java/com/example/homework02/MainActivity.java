package com.example.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UserFragment.IHome, FilterFragment.IFilter, SortFragment.ISort {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_view, new UserFragment(this), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void filterUsers(List<DataServices.User> users) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, FilterFragment.newInstance(users, this), getString(R.string.fragment_tag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sortUsers(List<DataServices.User> users) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, SortFragment.newInstance(users, this), getString(R.string.fragment_tag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void applyFilter(List<DataServices.User> users) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, UserFragment.newInstance(users, this), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void sort(List<DataServices.User> users) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, UserFragment.newInstance(users, this), getString(R.string.fragment_tag))
                .commit();
    }
}