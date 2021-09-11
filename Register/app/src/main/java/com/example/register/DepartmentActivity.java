package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DepartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Button selectDeptBtn = findViewById(R.id.select_btn);
        Button cancelBtn = findViewById(R.id.cancel_dept_btn);
        RadioGroup radioGroup = findViewById(R.id.dept_radio_grp);

        Intent department_intent = getIntent();
        final UserDetails userDetails = MainActivity.hasUserDetails(department_intent) ?
                (UserDetails) department_intent.getSerializableExtra(MainActivity.USER_DETAILS) :
                null;
        if(userDetails != null && userDetails.getDept() != null) {
            int numberOfRadioBtn = radioGroup.getChildCount();
            for(int i=0;i<numberOfRadioBtn;i++) {
                RadioButton currBtn = (RadioButton) radioGroup.getChildAt(i);
                if(currBtn.getText().equals(userDetails.getDept())) {
                    currBtn.setChecked(true);
                    break;
                }
            }
        }
        Intent intent = new Intent(DepartmentActivity.this, MainActivity.class);
        selectDeptBtn.setOnClickListener(view -> {
            RadioButton selectedDept = findViewById(radioGroup.getCheckedRadioButtonId());
            userDetails.setDept(selectedDept.getText().toString());
            intent.putExtra(MainActivity.USER_DETAILS, userDetails);
            startActivity(intent);
        });

        cancelBtn.setOnClickListener(view -> {
            intent.putExtra(MainActivity.USER_DETAILS, userDetails);
            startActivity(intent);
        });
    }
}