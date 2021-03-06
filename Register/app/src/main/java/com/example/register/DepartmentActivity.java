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
        String dept = department_intent.getStringExtra(MainActivity.DEPARTMENT);

        int numberOfRadioBtn = radioGroup.getChildCount();
        for (int i = 0; i < numberOfRadioBtn; i++) {
            RadioButton currBtn = (RadioButton) radioGroup.getChildAt(i);
            if (currBtn.getText().equals(dept)) {
                currBtn.setChecked(true);
                break;
            }
        }
        Intent intent = new Intent();
        selectDeptBtn.setOnClickListener(view -> {
            RadioButton selectedDept = findViewById(radioGroup.getCheckedRadioButtonId());
            intent.putExtra(MainActivity.DEPARTMENT, selectedDept.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        cancelBtn.setOnClickListener(view -> {
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }
}