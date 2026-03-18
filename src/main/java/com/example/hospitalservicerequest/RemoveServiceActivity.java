package com.example.hospitalservicerequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RemoveServiceActivity extends AppCompatActivity {

    EditText etServiceNameDelete;
    Button btnDeleteService;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_service);

        etServiceNameDelete = findViewById(R.id.et_service_name_delete);
        btnDeleteService = findViewById(R.id.btn_delete_service);
        dbHelper = new DatabaseHelper(this);

        btnDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = etServiceNameDelete.getText().toString().trim();

                if(serviceName.isEmpty()){
                    Toast.makeText(RemoveServiceActivity.this,
                            getString(R.string.toast_empty_fields), Toast.LENGTH_SHORT).show();
                } else {
                    boolean deleted = dbHelper.deleteServiceByName(serviceName);
                    if(deleted){
                        Toast.makeText(RemoveServiceActivity.this,
                                getString(R.string.toast_service_deleted), Toast.LENGTH_SHORT).show();
                        etServiceNameDelete.setText("");
                    } else {
                        Toast.makeText(RemoveServiceActivity.this,
                                "Service not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}