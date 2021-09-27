package com.example.inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ProfileForm.IProfileListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_view, new ProfileForm(), "fragment_tag")
                .commit();

    }

    @Override
    public void submitProfile(User user) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, HomeFragment.newInstance(user), "fragment_tag")
                .commit();
    }
}