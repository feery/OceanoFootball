package com.fycedwin.oceanofootball.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fycedwin.oceanofootball.R;
import com.fycedwin.oceanofootball.activity.MapsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.fycedwin.oceanofootball.app.AppController.TAG;

/**
 * Created by Feery on 7/11/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String>  Jam, Title, TipeHarga, Harga, Status, id_slot;
    private String Tanggal,Situasi_Jam,Bulan;

    public RecyclerViewAdapter(
            ArrayList<String> data_id_slot,
            ArrayList<String> dataJam,
            ArrayList<String>dataTitle,
            ArrayList<String> dataTipeHarga,
            ArrayList<String> dataHarga,
            ArrayList<String>dataStatus,
            String dataTanggal,
            String dataSituasiJam,
            String dataBulan) {

        id_slot=data_id_slot;
        Jam=dataJam;
        Title=dataTitle;
        TipeHarga=dataTipeHarga;
        Harga = dataHarga;
        Status = dataStatus;
        Tanggal= dataTanggal;
        Situasi_Jam= dataSituasiJam;
        Bulan= dataBulan;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView view_title;
        private TextView view_jam;
        private TextView view_harga;
        private TextView view_tipe;
        private CardView cardView;
        private String tempat ;
        private TextView kondisi;
        private TextView mvt_tanggal;


        ViewHolder(View v) {
            super(v);
            view_jam = v.findViewById(R.id.tv_jam);
            view_title = v.findViewById(R.id.tv_title);
            view_harga = v.findViewById(R.id.tv_harga);
            view_tipe = v.findViewById(R.id.tv_tipe);
            cardView = v.findViewById(R.id.cv_main);
            kondisi = v.findViewById(R.id.slotleft);
            mvt_tanggal=v.findViewById(R.id.mt_tanggal);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String no_idSlot = id_slot.get(position);

        holder.view_title.setText(Title.get(position));
        holder.view_jam.setText(Jam.get(position));
        holder.view_tipe.setText(TipeHarga.get(position));
        holder.view_harga.setText("Rp." + Harga.get(position));
        holder.tempat=Status.get(position);
        holder.mvt_tanggal.setText(Tanggal);





        int panjang_tanggal = holder.mvt_tanggal.getText().length();
        String bidik  =holder.mvt_tanggal.getText().toString();

        Date currentTime = Calendar.getInstance().getTime();
         SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");
         String cvb_date = timeformat.format(currentTime);

        int panjang_jam = holder.view_jam.getText().length();
        String JAM= holder.view_jam.getText().toString();
        String av_MENIT= "";
        String av_JAM ="";
        av_MENIT=av_MENIT+JAM.charAt(panjang_jam-3)+JAM.charAt(panjang_jam-2);
        av_JAM=av_JAM+JAM.charAt(panjang_jam-6)+JAM.charAt(panjang_jam-5);
        String mpv_Jam = av_JAM+":"+av_MENIT;

        Log.d(TAG, "Tanggal dumy : " + bidik);
        Log.d(TAG, "Tanggal uji : " + cvb_date);
        if(Situasi_Jam.compareTo(mpv_Jam)<1 || cvb_date.compareTo(bidik)<0){


            if(holder.tempat.equals("Kosong")){
                // Set onclicklistener pada view cvMain (CardView)
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Bundle dataBundel= new Bundle();
                        dataBundel.putString("id_slot",no_idSlot);
                        dataBundel.putString("jam",holder.view_jam.getText().toString());
                        dataBundel.putString("title",holder.view_title.getText().toString());
                        dataBundel.putString("harga",holder.view_harga.getText().toString());
                        dataBundel.putString("tipe",holder.view_tipe.getText().toString());
                        dataBundel.putString("tanggal",Tanggal);
                        Intent intent=new Intent(view.getContext(), MapsActivity.class);
                        intent.putExtras(dataBundel);
                        view.getContext().startActivity(intent);

                    }
                });
            }
            else{

                holder.kondisi.setVisibility(View.VISIBLE);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder ad= new AlertDialog.Builder(view.getContext());
                        ad.setMessage("Sorry Slot out ! ");
                        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        ad.show();

                    }
                });
            }
        }

        else {

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder ad= new AlertDialog.Builder(view.getContext());
                    ad.setMessage("Sorry Time out ! ");
                    ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    ad.show();

                }
            });

        }

    }

    @Override
    public int getItemCount() {

        return id_slot.size();
    }


}

