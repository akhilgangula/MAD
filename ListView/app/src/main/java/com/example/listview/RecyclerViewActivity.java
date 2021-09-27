package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity implements UserRecyclerAdapter.IUserListener {

    RecyclerView.Adapter adapter;
    ArrayList<User> data;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        data = new ArrayList<>();
        data.add(new User("Akhil", 25));
        data.add(new User("Vasu",26));
        data.add(new User("Shubham", 27));

        recyclerView = findViewById(R.id.recylcer_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserRecyclerAdapter(data, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void gotoMainActivity(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}