package com.adamevg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EmployeeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        Intent intent = getIntent();
        String name="";
        if (intent != null) {
            name = intent.getStringExtra("NAME");
        }
        TextView textView = findViewById(R.id.textView);
        textView.setText(name);
    }
}