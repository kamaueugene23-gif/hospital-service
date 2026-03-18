package com.example.hospitalservicerequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteUserActivity extends AppCompatActivity {

    EditText etUsernameDelete;
    Button btnDeleteUser;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        etUsernameDelete = findViewById(R.id.et_username_delete);
        btnDeleteUser = findViewById(R.id.btn_delete_user);
        dbHelper = new DatabaseHelper(this);

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsernameDelete.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(DeleteUserActivity.this, "Enter a username", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isDeleted = dbHelper.deleteUser(username);
                    if (isDeleted) {
                        Toast.makeText(DeleteUserActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                        etUsernameDelete.setText("");
                    } else {
                        Toast.makeText(DeleteUserActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}