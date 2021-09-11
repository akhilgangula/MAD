package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    public static final String NAME= "name";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String DEPARTMENT = "dept";
    public static final String USER_DETAILS = "details";

    public static boolean hasUserDetails(Intent intent) {
        return (intent != null &&
                intent.getExtras() != null &&
                intent.getSerializableExtra(USER_DETAILS) != null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selectDept = findViewById(R.id.select_dept_btn);
        Button submitBtn = findViewById(R.id.submitBtn);
        EditText name = findViewById(R.id.nameField);
        EditText email = findViewById(R.id.emailField);
        EditText id = findViewById(R.id.idField);
        TextView dept = findViewById(R.id.dept_value);

        Intent mainIntent = getIntent();
        if(mainIntent != null && mainIntent.getExtras() != null && mainIntent.hasExtra(USER_DETAILS)) {
            UserDetails userDetails = (UserDetails)mainIntent.getSerializableExtra(USER_DETAILS);
            name.setText(userDetails.getName());
            email.setText(userDetails.getEmail());
            id.setText(userDetails.getId());
            dept.setText(userDetails.getDept());
        }
        selectDept.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
            UserDetails userDetails = new UserDetails();
            userDetails.setName(name.getText().toString());
            userDetails.setEmail(email.getText().toString());
            userDetails.setId(id.getText().toString());
            userDetails.setDept(dept.getText().toString());
            intent.putExtra(USER_DETAILS, userDetails);
            startActivity(intent);
        });
        submitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            UserDetails userDetails = new UserDetails();
            userDetails.setName(name.getText().toString());
            userDetails.setEmail(email.getText().toString());
            userDetails.setId(id.getText().toString());
            userDetails.setDept(dept.getText().toString());
            intent.putExtra(USER_DETAILS, userDetails);
            startActivity(intent);
        });
    }
}