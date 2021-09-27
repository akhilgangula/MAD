package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter adapter;
    ArrayList<User> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<>();
        data.add(new User("Akhil", 25));
        data.add(new User("Vasu",26));
        data.add(new User("Shubham", 27));
        ListView listView = findViewById(R.id.listViewContainer);
        adapter = new UserAdapter(this, R.layout.user_card, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            data.remove(i);
            adapter.notifyDataSetChanged();
        });
    }
}