package com.example.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String USER_DETAILS = "details";
    public static final String DEPARTMENT = "dept";
    public static final int LAUNCH_DEPARTMENT_ACTIVITY = 1;
    public final String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public static boolean hasUserDetails(Intent intent) {
        return (intent != null &&
                intent.getExtras() != null &&
                intent.getSerializableExtra(USER_DETAILS) != null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(LAUNCH_DEPARTMENT_ACTIVITY == requestCode) {
            if(resultCode == Activity.RESULT_OK) {
                TextView dept = findViewById(R.id.dept_value);
                dept.setText(data.getStringExtra(DEPARTMENT));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        findViewById(R.id.select_dept_btn).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
            intent.putExtra(DEPARTMENT, dept.getText().toString());
            startActivityForResult(intent, LAUNCH_DEPARTMENT_ACTIVITY);

        });
        findViewById(R.id.submitBtn).setOnClickListener(view -> {
            String nameValue = name.getText().toString();
            String emailValue = email.getText().toString();
            String idValue = id.getText().toString();
            String deptValue = dept.getText().toString();

            try {
                if(nameValue == null || nameValue.equals("")) {
                    throw new Exception(getString(R.string.validate_name));
                }

                if(emailValue == null || emailValue.equals("") || !emailValue.matches(regex)) {
                    throw new Exception(getString(R.string.validate_email));
                }
                if(idValue == null || idValue.equals("")) {

                    throw new Exception(getString(R.string.validate_id));
                }
                if(deptValue == null || deptValue.equals("")) {
                    throw new Exception(getString(R.string.validate_dept));
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

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