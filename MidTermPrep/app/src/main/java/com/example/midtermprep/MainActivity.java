package com.example.midtermprep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String ALERT_FRAGMENT = "alert_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.containerView, new AlertFragment(), ALERT_FRAGMENT)
//                .commit();
        Intent intent = new Intent(MainActivity.this, ExplictActivity.class);
        startActivity(intent);
    }
}