package com.example.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcBtn = findViewById(R.id.calcBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        EditText priceInput = findViewById(R.id.priceInput);
        RadioGroup discountRadioBtn = findViewById(R.id.discountRadioBtn);
        RadioButton defaultDiscPrice = findViewById(R.id.fivePercentBtn);
        TextView discountPrice = findViewById(R.id.discPrice);

        // clear button listener
        clearBtn.setOnClickListener(view -> {
            priceInput.setText("");
            discountPrice.setText("");
            defaultDiscPrice.setChecked(true);
        });

        // calculate button listener
        calcBtn.setOnClickListener(view -> {
                String priceValue = priceInput.getText().toString();
                if(priceValue.equals("") || Double.parseDouble(priceValue) <= 0) {
                    Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_SHORT).show();
                    return;
                }
                double price = Double.parseDouble(priceValue);
                String discValue = ((RadioButton)(findViewById(discountRadioBtn.getCheckedRadioButtonId()))).getText().toString();
                double discount = Double.parseDouble(discValue.replace("%", ""));
                double discountedPrice = price - (discount * price) / 100;
                discountPrice.setText(String.format(Locale.US, "%.2f",discountedPrice));
        });
    }
}