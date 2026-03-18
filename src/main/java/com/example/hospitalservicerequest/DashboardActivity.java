package com.example.hospitalservicerequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button requestServiceBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        requestServiceBtn = findViewById(R.id.requestServiceBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        requestServiceBtn.setOnClickListener(v -> {

            startActivity(new Intent(this, RequestServiceActivity.class));

        });

        logoutBtn.setOnClickListener(v -> {

            startActivity(new Intent(this, LoginActivity.class));
            finish();

        });
    }
}