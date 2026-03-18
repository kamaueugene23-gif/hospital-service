package com.example.hospitalservicerequest;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewRequestsActivity extends AppCompatActivity {

    ListView lvRequests;
    DatabaseHelper db;
    ArrayList<String> requestList;
    ArrayList<Integer> requestIdList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        lvRequests = findViewById(R.id.lvRequests);
        db = new DatabaseHelper(this);
        requestList = new ArrayList<>();
        requestIdList = new ArrayList<>();

        loadRequests();

        lvRequests.setOnItemClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
        });
    }

    private void loadRequests() {
        requestList.clear();
        requestIdList.clear();
        Cursor cursor = db.getAllRequests();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Requests Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                requestIdList.add(cursor.getInt(0)); // ID
                String data = "Service: " + cursor.getString(2) + 
                             "\nNotes: " + cursor.getString(3) +
                             "\nWard: " + cursor.getString(4) + 
                             "\nBed: " + cursor.getString(5) +
                             "\nStatus: " + cursor.getString(6);
                requestList.add(data);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestList);
        lvRequests.setAdapter(adapter);
        cursor.close();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Request")
                .setMessage("Are you sure you want to delete this request?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    int requestId = requestIdList.get(position);
                    if (db.deleteRequest(requestId)) {
                        Toast.makeText(this, "Request deleted", Toast.LENGTH_SHORT).show();
                        loadRequests();
                    } else {
                        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
