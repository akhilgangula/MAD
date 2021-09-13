package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView nameField = findViewById(R.id.name_text);
        TextView emailField = findViewById(R.id.email_text);
        TextView idField = findViewById(R.id.id_text);
        TextView deptField = findViewById(R.id.dept_text);
        Intent profileIntent = getIntent();
        getString(R.string.computer_science_label);
        UserDetails userDetails = MainActivity.hasUserDetails(profileIntent) ?
                (UserDetails) profileIntent.getSerializableExtra(MainActivity.USER_DETAILS) :
                null;
        if(profileIntent != null && profileIntent.getExtras() != null) {
            nameField.setText(userDetails.getName());
            emailField.setText(userDetails.getEmail());
            idField.setText(userDetails.getId());
            deptField.setText(userDetails.getDept());
        }
    }
}