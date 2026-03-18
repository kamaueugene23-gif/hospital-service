package com.example.hospitalservicerequest;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RequestServiceActivity extends AppCompatActivity {

    Spinner serviceSpinner;
    EditText notes, ward, bed;
    Button submitBtn;

    DatabaseHelper db;

    String[] services = {
            "Cleaning",
            "Equipment Assistance",
            "Linen Change",
            "Porter Services"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        serviceSpinner = findViewById(R.id.serviceSpinner);
        notes = findViewById(R.id.notes);
        ward = findViewById(R.id.ward);
        bed = findViewById(R.id.bed);
        submitBtn = findViewById(R.id.submitBtn);

        db = new DatabaseHelper(this);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                services);

        serviceSpinner.setAdapter(adapter);

        submitBtn.setOnClickListener(v -> {

            db.addRequest(
                    serviceSpinner.getSelectedItem().toString(),
                    notes.getText().toString(),
                    ward.getText().toString(),
                    bed.getText().toString()
            );

            Toast.makeText(this,"Service Request Submitted",Toast.LENGTH_SHORT).show();

            notes.setText("");
            ward.setText("");
            bed.setText("");
        });
    }
}