package com.example.hospitalservicerequest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HospitalService.db";
    private static final int DATABASE_VERSION = 3;

    // User table
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROLE = "role";

    // Services table
    private static final String TABLE_SERVICES = "services";
    private static final String COL_SERVICE_ID = "id";
    private static final String COL_SERVICE_NAME = "service_name";
    private static final String COL_SERVICE_DESC = "service_desc";

    // Service Requests table
    private static final String TABLE_REQUESTS = "requests";
    private static final String COL_REQUEST_ID = "id";
    private static final String COL_REQUEST_USER = "username";
    private static final String COL_REQUEST_SERVICE = "service_name";
    private static final String COL_REQUEST_NOTES = "notes";
    private static final String COL_REQUEST_WARD = "ward";
    private static final String COL_REQUEST_BED = "bed";
    private static final String COL_REQUEST_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT UNIQUE,"
                + COL_PASSWORD + " TEXT,"
                + COL_ROLE + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Services table
        String CREATE_SERVICES_TABLE = "CREATE TABLE " + TABLE_SERVICES + "("
                + COL_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_SERVICE_NAME + " TEXT,"
                + COL_SERVICE_DESC + " TEXT)";
        db.execSQL(CREATE_SERVICES_TABLE);

        // Create Requests table
        String CREATE_REQUESTS_TABLE = "CREATE TABLE " + TABLE_REQUESTS + "("
                + COL_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_REQUEST_USER + " TEXT,"
                + COL_REQUEST_SERVICE + " TEXT,"
                + COL_REQUEST_NOTES + " TEXT,"
                + COL_REQUEST_WARD + " TEXT,"
                + COL_REQUEST_BED + " TEXT,"
                + COL_REQUEST_STATUS + " TEXT DEFAULT 'Pending')";
        db.execSQL(CREATE_REQUESTS_TABLE);

        // Pre-insert an admin user
        ContentValues adminValues = new ContentValues();
        adminValues.put(COL_USERNAME, "admin");
        adminValues.put(COL_PASSWORD, "admin123");
        adminValues.put(COL_ROLE, "admin");
        db.insert(TABLE_USERS, null, adminValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        onCreate(db);
    }

    // =======================
    // User Methods
    // =======================

    // Register user
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_ROLE, "user"); // Default role
        long result = db.insert(TABLE_USERS, null, cv);
        return result != -1; // true if inserted successfully
    }

    // Check user login and return role
    public String getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_ROLE + " FROM " + TABLE_USERS +
                        " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password});
        
        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(0);
        }
        cursor.close();
        return role;
    }

    // Get all users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    // Delete user
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USERS, COL_USERNAME + "=?", new String[]{username});
        return result > 0;
    }

    // =======================
    // Service Methods
    // =======================

    // Add a service
    public boolean addService(String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_SERVICE_NAME, name);
        cv.put(COL_SERVICE_DESC, desc);
        long result = db.insert(TABLE_SERVICES, null, cv);
        return result != -1;
    }

    // Get all services
    public Cursor getAllServices() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SERVICES, null);
    }

    // Update service
    public boolean updateService(int id, String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_SERVICE_NAME, name);
        cv.put(COL_SERVICE_DESC, desc);
        int result = db.update(TABLE_SERVICES, cv, COL_SERVICE_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }


    // Delete service
    public boolean deleteService(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_SERVICES, COL_SERVICE_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteServiceByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_SERVICES, COL_SERVICE_NAME + "=?", new String[]{name});
        return result > 0;
    }

    // =======================
    // Requests Methods
    // =======================

    // Add service request
    public boolean addRequest(String serviceName, String notes, String ward, String bed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_REQUEST_SERVICE, serviceName);
        cv.put(COL_REQUEST_NOTES, notes);
        cv.put(COL_REQUEST_WARD, ward);
        cv.put(COL_REQUEST_BED, bed);
        cv.put(COL_REQUEST_STATUS, "Pending");
        long result = db.insert(TABLE_REQUESTS, null, cv);
        return result != -1;
    }

    // Get all requests
    public Cursor getAllRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_REQUESTS, null);
    }
    

    // Delete request
    public boolean deleteRequest(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_REQUESTS, COL_REQUEST_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }
}
