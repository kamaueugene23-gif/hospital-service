package com.example.hospitalservicerequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    Button viewRequests, viewUsers, addService, removeService, updateService, deleteUser, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        viewRequests = findViewById(R.id.btnViewRequests);
        addService = findViewById(R.id.btnAddService);
        removeService = findViewById(R.id.btnRemoveService);
        deleteUser = findViewById(R.id.btnDeleteUser);
        logout = findViewById(R.id.btnLogout);

        // View Requests
        viewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, ViewRequestsActivity.class);
                startActivity(intent);
            }
        });


        // Add Service
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, AddServiceActivity.class);
                startActivity(intent);
            }
        });

        // Remove Service
        removeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, RemoveServiceActivity.class);
                startActivity(intent);
            }
        });


        // Delete User
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        // Logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
