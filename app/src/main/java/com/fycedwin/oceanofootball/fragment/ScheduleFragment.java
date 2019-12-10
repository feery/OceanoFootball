package com.fycedwin.oceanofootball.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.fycedwin.oceanofootball.app.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

import static com.fycedwin.oceanofootball.app.AppController.TAG;


public class ScheduleFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String>dataJam;
    private ArrayList<String>dataTitle;
    private ArrayList<String>dataTipeHarga;
    private ArrayList<String>dataHargaWeekDay;
    private ArrayList<String>dataHargaWeekEnd;
    private ArrayList<String>dataStatus;
    private ArrayList<String>dataIdSlot;
    private Date currentTime = Calendar.getInstance().getTime();
    private SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");
    private String cvb_date = timeformat.format(currentTime);


    private String TOKEN ="a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
    private String tanggal;
    private ProgressDialog progressDialog;
    private RelativeLayout v_koneksi;
    private RelativeLayout v_konten;


    public ScheduleFragment(){
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        dataJam = new ArrayList<>();
        dataTitle= new ArrayList<>();
        dataTipeHarga= new ArrayList<>();
        dataHargaWeekDay= new ArrayList<>();
        dataHargaWeekEnd= new ArrayList<>();
        dataStatus= new ArrayList<>();
        dataIdSlot= new ArrayList<>();
        rvView = (RecyclerView) rootView.findViewById(R.id.rv_main);
        v_koneksi=(RelativeLayout)rootView.findViewById(R.id.av_jaringan);
        v_konten=(RelativeLayout)rootView.findViewById(R.id.av_content);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvView.setLayoutManager(layoutManager);


        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH,1);

        /** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(
                rootView, R.id.calendarView).startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatUser =new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatHari = new SimpleDateFormat("EEEE");
                SimpleDateFormat jam = new SimpleDateFormat("kk:mm");
                SimpleDateFormat dateBulan = new SimpleDateFormat("MMMM");
                String bulan= dateBulan.format(date);
                Log.e(TAG, "Bulan adalah: " + bulan);
                String Jam = jam.format(date);
                String Hari = dateFormatHari.format(date);
                String Tanggal_Booking= dateFormatUser.format(date);
                tanggal=dateFormat.format(date);
                cvb_date=tanggal;
                initDataSet(tanggal,TOKEN);

                if (Hari.equals("Sunday") || Hari.equals("Saturday")) {

                    adapter = new RecyclerViewAdapter(dataIdSlot,dataJam,dataTitle,dataTipeHarga,dataHargaWeekEnd,dataStatus,Tanggal_Booking,Jam,bulan);
                }
                else{
                    adapter = new RecyclerViewAdapter(dataIdSlot,dataJam, dataTitle, dataTipeHarga, dataHargaWeekDay, dataStatus,Tanggal_Booking,Jam,bulan);
                }


            }
        });

        return rootView;
    }



    public void updateNewsList(){
        rvView.setAdapter(adapter);

    }


   public void initDataSet(final String o_tanggal, final String TOKEN){



        String tag_string_req = "req_data";
        progressDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_SLOT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String ststus = jObj.getString("status");
                    JSONArray a = jObj.getJSONArray("collections");
                    Log.e(TAG, "Status: " + ststus);
                    Log.e(TAG, "collections: " + a);
                    int len = a.length();
                    dataIdSlot.clear();
                    dataJam.clear();
                    dataTitle.clear();
                    dataTipeHarga.clear();
                    dataHargaWeekDay.clear();
                    dataHargaWeekEnd.clear();
                    dataStatus.clear();
                    for(int i =0;i<len;i++){


                        JSONObject item= a.getJSONObject(i);
                        dataIdSlot.add(item.get("id_slot").toString());
                        dataJam.add(item.get("jam").toString());
                        dataTitle.add(item.get("title").toString());
                        dataTipeHarga.add(item.get("tipe_harga").toString());
                        dataHargaWeekDay.add(item.get("harga_weekday").toString());
                        dataHargaWeekEnd.add(item.get("harga_weekend").toString());
                        dataStatus.add(item.get("status").toString());
                    }
                    updateNewsList();

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
                    v_konten.setVisibility(View.GONE);
                    v_koneksi.setVisibility(View.VISIBLE);
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {

                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    v_konten.setVisibility(View.GONE);
                    v_koneksi.setVisibility(View.VISIBLE);
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    v_konten.setVisibility(View.GONE);
                    v_koneksi.setVisibility(View.VISIBLE);
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                Toast.makeText(getActivity().getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("tanggal",o_tanggal);
            params.put("_token",TOKEN);
            return params;
        }



    };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    @Override
    public void onResume() {
        super.onResume();
        initDataSet(cvb_date,TOKEN);
        Toast.makeText(getContext(), "On Resume", Toast.LENGTH_SHORT).show();
    }

}
