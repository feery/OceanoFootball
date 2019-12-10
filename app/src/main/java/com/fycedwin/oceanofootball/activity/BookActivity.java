package com.fycedwin.oceanofootball.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fycedwin.oceanofootball.R;
import com.fycedwin.oceanofootball.app.AppConfig;
import com.fycedwin.oceanofootball.app.AppController;
import com.fycedwin.oceanofootball.helper.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookActivity extends AppCompatActivity {
    private static final String TAG = BookActivity.class.getSimpleName();
    private  RadioButton transfer,credit;
    private Button Booking;
    private String TOKEN ="a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
    private DatabaseManager db;
    private ProgressDialog progressDialog;
    private EditText mkt_keterangan;
    private String  a_idSlot, a_jam , a_title, a_harga, a_tanggal, a_tipe ;
    private TextView ov_title,ov_harga1,ov_haga2,ov_jam,ov_tanggal,ov_tipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        progressDialog = new ProgressDialog(this);
        mkt_keterangan=(EditText)findViewById(R.id.mvp_keterangan);
        progressDialog.setCancelable(false);
        transfer=(RadioButton)findViewById(R.id.tranferRadio);
        credit=(RadioButton)findViewById(R.id.CreditRadio);
        ov_title =(TextView)findViewById(R.id.aov_title);
        ov_harga1= (TextView)findViewById(R.id.aov1_harga);
        ov_haga2= (TextView)findViewById(R.id.aov2_harga);
        ov_jam = (TextView)findViewById(R.id.aov_jam);
        ov_tipe=(TextView)findViewById(R.id.aov_tipe);
        ov_tanggal=(TextView)findViewById(R.id.aov_tanggal);
        Booking=(Button)findViewById(R.id.bookingTransaksi);

        Bundle extras=getIntent().getExtras();
        if(extras!=null){

            a_idSlot= extras.getString("mkt_id_slot");
            a_jam = extras.getString("mkt_jam");
            a_title = extras.getString("mkt_title");
            a_harga = extras.getString("mkt_harga");
            a_tanggal = extras.getString("mkt_tanggal");
            a_tipe = extras.getString("mkt_tipe");


            ov_title.setText(a_title);
            ov_harga1.setText(a_harga);
            ov_haga2.setText(a_harga);
            ov_jam.setText(a_jam);
            ov_tipe.setText(a_tipe);
            ov_tanggal.setText(a_tanggal);



        }

        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(transfer.isChecked()){

                    db = new DatabaseManager(getApplicationContext());

                    HashMap<String, String> user = db.getUserDetails();

                    String name_database = user.get("name");
                    String email_database = user.get("email");
                    String phone_datebase =user.get("phone");
                    String address_database=user.get("address");
                    String a_kererangan = mkt_keterangan.getText().toString();
                    if(a_kererangan.equals(null)|| a_kererangan.equals("")){
                        a_kererangan="Tidak ada ";
                    }
                    Log.d(TAG, "Bukti ada : " + a_tanggal+", "+TOKEN+" , "+a_idSlot+" , "+ name_database+" , "
                            +email_database+" , "+phone_datebase+" , "+address_database+" , "+a_kererangan);

                    postDataSet(a_tanggal,TOKEN,a_idSlot,name_database,address_database,phone_datebase,email_database,a_kererangan);
//                    Intent intent = new Intent(getApplicationContext(),TransferActivity.class);
//                    startActivity(intent);
                }
                else if(credit.isChecked()){
                    Intent intent = new Intent(getApplicationContext(),PaymetActivity.class);
                    startActivity(intent);
                }
                else{
                    showDialogPayment();
                }

            }
        });

    }
    private  void showDialogPayment(){
        AlertDialog.Builder ad= new AlertDialog.Builder(this);

        ad.setMessage("Please select a payment method?");
        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
    }

    private void postDataSet(final String mkt_tanggal, final String mkt_token, final String mkt_idSlot, final String mkt_nama,
                             final String mkt_alamat, final String mkt_phone, final String mkt_email, final String mkt_keterangan) {

        String tag_string_req = "POST :";
        progressDialog.setMessage("Loading ...");
        showDialogProges();

        StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_BOOKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String ststus = jObj.getString("status");
                    String collections = jObj.getString("collections");
                    JSONObject inti = jObj.getJSONObject("collections");
                    String x_id = inti.getString("id");
                    String x_kodeBooking = inti.getString("kode_booking");
                    String x_nama = inti.getString("nama");
                    String x_waktu = inti.getString("waktu_reservasi");
                    String x_alamat = inti.getString("alamat");
                    String x_telepon = inti.getString("telepon");
                    String x_email = inti.getString("email");
                    String x_dp = inti.getString("dp");
                    String x_sisa = inti.getString("sisa");
                    String x_total = inti.getString("total");
                    String x_ketHarga = inti.getString("ket_harga");
                    String x_ketReservasi = inti.getString("ket_reservasi");
                    String x_tanggal = inti.getString("tanggal_reservasi");
                    String x_idSlot = inti.getString("id_slot");
                    String x_slot = inti.getString("slot");
                    String x_idHarga = inti.getString("id_harga");
                    String x_tipe = inti.getString("tipe_harga");
                    String x_staus = inti.getString("status_reservasi");

                    Log.e(TAG, "Status: " + ststus);
                    Log.e(TAG,"collections :"+collections);
                    Log.e(TAG, "id: " + x_id);

                    try{
                        db.insertBooking(x_id,
                                x_kodeBooking,x_nama,x_waktu,x_alamat,
                                x_telepon,x_email,x_dp,x_sisa,
                                x_total,x_ketHarga,x_ketReservasi,
                                x_tanggal,x_idSlot,x_slot,x_idHarga,x_tipe,x_staus);
                    }
                    catch (Exception e){
                        Log.e(TAG, "error insert booking: " + e);
                    }


                    Intent intent = new Intent(getApplicationContext(),TransferActivity.class);
                    startActivity(intent);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Json Error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Respons Error: " + error.getMessage());
                hideDialog();
                String message = null;
                if (error instanceof NetworkError) {

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {

                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {

                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {

                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("tanggal",mkt_tanggal);
                params.put("_token",mkt_token);
                params.put("id_slot",mkt_idSlot);
                params.put("nama",mkt_nama);
                params.put("alamat",mkt_alamat);
                params.put("telepon",mkt_phone);
                params.put("email",mkt_email);
                params.put("ket_reservasi",mkt_keterangan);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void showDialogProges() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
