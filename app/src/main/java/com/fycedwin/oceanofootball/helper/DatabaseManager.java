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

/**
 * Created by Feery on 8/9/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DB_Name="Oceano.db";

    private static final String Table_Booking = "reservasi";
    protected  static  final  String Colom_idBooking = "id";
    protected  static  final  String Colom_kodeBooking = "kode_booking";
    protected  static  final  String Colom_nama = "nama";
    protected  static  final  String Colom_waktuReservasi = "waktu_reservasi";
    protected  static  final  String Colom_alamat = "alamat";
    protected  static  final  String Colom_telepon = "telepon";
    protected  static  final  String Colom_emailReservasi = "email";
    protected  static  final  String Colom_dp = "dp";
    protected  static  final  String Colom_sisa="sisa";
    protected  static  final  String Colom_total="total";
    protected  static  final  String Colom_ketHarga="ket_harga";
    protected  static  final  String Colom_ketReservasi="ket_reservasi";
    protected  static  final  String Colom_tanggalReservasi="tanggal_reservasi";
    protected  static  final  String Colom_idSlot="id_slot";
    protected  static  final  String Colom_slot="slot";
    protected  static  final  String Colom_idHarga="id_harga";
    protected  static  final  String Colom_tipeHarga="tipe_harga";
    protected  static  final  String Colom_statusReservasi="status_reservasi";

    private static final String Table ="User";
    protected static final String Colom_id ="id";
    protected static final String Colom_name ="name";
    protected static final String Colom_email ="email";
    protected static final String Colom_phone ="phone";
    protected static final String Colom_address ="address";


    public DatabaseManager(Context context){
        super(context,DB_Name,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+ Table +"("
                        +Colom_id +" INTEGER PRIMARY KEY,"
                        +Colom_name +" TEXT,"
                        +Colom_email +" TEXT,"
                        + Colom_phone +" TEXT,"
                        + Colom_address +" TEXT)"
        );
        db.execSQL(
                "CREATE TABLE "+ Table_Booking +" ("
                        +Colom_idBooking + " TEXT,"
                        +Colom_kodeBooking + " TEXT,"
                        +Colom_nama + " TEXT,"
                        +Colom_waktuReservasi + " TEXT,"
                        +Colom_alamat + " TEXT,"
                        +Colom_telepon + "TEXT,"
                        +Colom_emailReservasi+ " TEXT,"
                        +Colom_dp + " TEXT,"
                        +Colom_sisa+ " TEXT,"
                        +Colom_total+ "TEXT,"
                        +Colom_ketHarga+ " TEXT,"
                        +Colom_ketReservasi+ " TEXT,"
                        +Colom_tanggalReservasi+ " TEXT,"
                        +Colom_idSlot + " TEXT,"
                        +Colom_slot + " TEXT,"
                        +Colom_idHarga + " TEXT,"
                        +Colom_tipeHarga + " TEXT,"
                        +Colom_statusReservasi + " TEXT)"

        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+Table);
        db.execSQL("DROP TABLE IF EXISTS"+Table_Booking);
        onCreate(db);
    }

    public void insert(String name,String phone,String email,String address){
        ContentValues cv = new ContentValues();
        cv.put(Colom_name,name);
        cv.put(Colom_email,email);
        cv.put(Colom_phone,phone);
        cv.put(Colom_address,address);
        SQLiteDatabase db= this.getWritableDatabase();
        db.insert(Table,null,cv);
        db.close();

    }
    public void insertBooking(String idBooking,
                              String kodeBooking,
                              String nama,
                              String waktuReservasi,
                              String alamat ,
                              String telepon,
                              String emailReservasi,
                              String dp,
                              String sisa,
                              String total,
                              String ketHarga,
                              String ketReservasi,
                              String tanggalReservasi,
                              String idSlot,
                              String slot,
                              String idHarga,
                              String tipeHarga,
                              String statusReservasi){
        ContentValues cv = new ContentValues();
        cv.put(Colom_idBooking,idBooking);
        cv.put(Colom_kodeBooking,kodeBooking);
        cv.put(Colom_nama,nama);
        cv.put(Colom_waktuReservasi,waktuReservasi);
        cv.put(Colom_alamat,alamat);
        cv.put(Colom_telepon,telepon);
        cv.put(Colom_emailReservasi,emailReservasi);
        cv.put(Colom_dp,dp);
        cv.put(Colom_sisa,sisa);
        cv.put(Colom_total,total);
        cv.put(Colom_ketHarga,ketHarga);
        cv.put(Colom_ketReservasi,ketReservasi);
        cv.put(Colom_tanggalReservasi,tanggalReservasi);
        cv.put(Colom_idSlot,idSlot);
        cv.put(Colom_slot,slot);
        cv.put(Colom_idHarga,idHarga);
        cv.put(Colom_tipeHarga,tipeHarga);
        cv.put(Colom_statusReservasi,statusReservasi);

        SQLiteDatabase db= this.getWritableDatabase();
        db.insert(Table_Booking,null,cv);
        db.close();

    }
//==================================================================================================

    public void update(Integer id,String name ,String Email, String phone){
        ContentValues cv = new ContentValues();
        cv.put(Colom_name,name);
        cv.put(Colom_email,Email);
        cv.put(Colom_phone,phone);
        SQLiteDatabase db= this.getWritableDatabase();
        db.update(Table,cv,Colom_id + " = ?",new String[]{Integer.toString(id)});
    }
    public void delete(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table,Colom_id+" = ?",new String[]{Integer.toString(id)});

    }
    public ArrayList<String> getAllContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+Table+" ORDER BY "+Colom_id+" ASC",null);
        c.moveToFirst();
        ArrayList<String> contacts= new ArrayList<>();
        while (!c.isAfterLast()){
            contacts.add(c.getString(c.getColumnIndex(Colom_name)));
            c.moveToNext();
        }
        c.close();
        return contacts;

    }
    public Dictionary<String,String> getContacts(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery(
                "SELECT * FROM "+ Table+" WHERE "+ Colom_id+" = "+ id,null);
        c.moveToFirst();
        Dictionary<String,String> contact=new Hashtable<>();
        contact.put(Colom_name,c.getString(c.getColumnIndex(Colom_name)));
        contact.put(Colom_name,c.getString(c.getColumnIndex(Colom_email)));
        contact.put(Colom_name,c.getString(c.getColumnIndex(Colom_phone)));
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
    public void deleteBooking() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Table_Booking, null, null);
        db.close();
        Log.d(TAG, "Deleted all Booking");
    }

    /**
     * Getting user data from database
     * */
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

    public HashMap<String, String> getBookingDetails() {
        HashMap<String, String> booking = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + Table_Booking;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            booking.put("id",cursor.getString(1));
            booking.put("kode_booking",cursor.getString(2));
            booking.put("nama",cursor.getString(3));
            booking.put("waktu_reservasi",cursor.getString(4));
            booking.put("alamat",cursor.getString(5));
            booking.put("telepon",cursor.getString(6));
            booking.put("email",cursor.getString(7));
            booking.put("dp",cursor.getString(8));
            booking.put("sisa",cursor.getString(9));
            booking.put("total",cursor.getString(10));
            booking.put("ket_harga",cursor.getString(11));
            booking.put("ket_reservasi",cursor.getString(12));
            booking.put("tanggal_reservasi",cursor.getString(13));
            booking.put("id_slot",cursor.getString(14));
            booking.put("slot",cursor.getString(15));
            booking.put("id_harga",cursor.getString(16));
            booking.put("tipe_harga",cursor.getString(17));
            booking.put("status_reservasi",cursor.getString(18));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + booking.toString());

        return booking;
    }
}
