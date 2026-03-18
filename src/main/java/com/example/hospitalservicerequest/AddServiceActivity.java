package com.example.hospitalservicerequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddServiceActivity extends AppCompatActivity {

    EditText etServiceName, etServiceDesc;
    Button btnSaveService;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        etServiceName = findViewById(R.id.et_service_name);
        etServiceDesc = findViewById(R.id.et_service_desc);
        btnSaveService = findViewById(R.id.btn_save_service);
        dbHelper = new DatabaseHelper(this);

        btnSaveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etServiceName.getText().toString().trim();
                String desc = etServiceDesc.getText().toString().trim();

                if(name.isEmpty() || desc.isEmpty()){
                    Toast.makeText(AddServiceActivity.this,
                            getString(R.string.toast_empty_fields), Toast.LENGTH_SHORT).show();
                } else {
                    boolean inserted = dbHelper.addService(name, desc);
                    if(inserted){
                        Toast.makeText(AddServiceActivity.this,
                                getString(R.string.toast_service_added), Toast.LENGTH_SHORT).show();
                        etServiceName.setText("");
                        etServiceDesc.setText("");
                    } else {
                        Toast.makeText(AddServiceActivity.this,
                                "Service already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}