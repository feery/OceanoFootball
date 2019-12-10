package com.fycedwin.oceanofootball.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import static com.fycedwin.oceanofootball.app.AppController.TAG;


public class Database extends SQLiteOpenHelper {

    private static final String DB_Name = "reservasi.db";
    private static final String Table = "User";
    protected static final String Colom_id = "id";
    protected static final String Colom_name = "name";
    protected static final String Colom_email = "email";
    protected static final String Colom_phone = "phone";
    protected static final String Colom_address = "address";

    public Database(Context context) {
        super(context, DB_Name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Table + "("
                        + Colom_id + " INTEGER PRIMARY KEY,"
                        + Colom_name + " TEXT,"
                        + Colom_email + " TEXT,"
                        + Colom_phone + " TEXT,"
                        + Colom_address + " TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Table);
        onCreate(db);
    }

    public void insert(String name, String phone, String email, String address) {
        ContentValues cv = new ContentValues();
        cv.put(Colom_name, name);
        cv.put(Colom_email, email);
        cv.put(Colom_phone, phone);
        cv.put(Colom_address, address);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Table, null, cv);

    }

    public void update(Integer id, String name, String Email, String phone) {
        ContentValues cv = new ContentValues();
        cv.put(Colom_name, name);
        cv.put(Colom_email, Email);
        cv.put(Colom_phone, phone);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(Table, cv, Colom_id + " = ?", new String[]{Integer.toString(id)});
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table, Colom_id + " = ?", new String[]{Integer.toString(id)});

    }

    public ArrayList<String> getAllContent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Table + " ORDER BY " + Colom_id + " ASC", null);
        c.moveToFirst();
        ArrayList<String> contacts = new ArrayList<>();
        while (!c.isAfterLast()) {
            contacts.add(c.getString(c.getColumnIndex(Colom_name)));
            c.moveToNext();
        }
        c.close();
        return contacts;

    }

    public Dictionary<String, String> getContacts(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + Table + " WHERE " + Colom_id + " = " + id, null);
        c.moveToFirst();
        Dictionary<String, String> contact = new Hashtable<>();
        contact.put(Colom_name, c.getString(c.getColumnIndex(Colom_name)));
        contact.put(Colom_name, c.getString(c.getColumnIndex(Colom_email)));
        contact.put(Colom_name, c.getString(c.getColumnIndex(Colom_phone)));
        c.close();
        return contact;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Table, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + Table;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("phone", cursor.getString(3));
            user.put("address", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
}