//package com.fycedwin.oceanofootball.app;
//
//import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.fycedwin.oceanofootball.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
///**
// * Created by Feery on 8/18/2017.
// */
//
//public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
//    private ArrayList<String> gambar;
//    private ArrayList<String> namaEvent;
//    private ArrayList<String>  judulEvent;
//    private ArrayList<String>  tanggalEvent;
//    private  View view;
//    public RecyclerViewAdapter2(ArrayList<String> Image, ArrayList<String> name, ArrayList<String> title, ArrayList<String>date){
//        gambar=Image;
//        namaEvent=name;
//        judulEvent=title;
//        tanggalEvent=date;
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView imageView;
//        TextView eventView;
//        TextView titleView;
//        TextView dateView;
//        private CardView cardView;
//
//        public ViewHolder(View itemView) {
//
//            super(itemView);
//            view=itemView;
//            imageView=(ImageView)itemView.findViewById(R.id.imageIklan);
//            eventView=(TextView)itemView.findViewById(R.id.txtEvent);
//            titleView=(TextView)itemView.findViewById(R.id.txtTitle);
//            dateView=(TextView)itemView.findViewById(R.id.txtTanggal);
//            cardView=(CardView)itemView.findViewById(R.id.CardViewIklan);
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // membuat view baru
//        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
//        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
//       // ViewHolder vh = new ViewHolder(v);
//       // return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Picasso.with(view.getContext()).load(gambar.get(position)).into(holder.imageView);
//
//        holder.eventView.setText(namaEvent.get(position));
//        holder.titleView.setText(judulEvent.get(position));
//        holder.dateView.setText(tanggalEvent.get(position));
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder ad= new AlertDialog.Builder(view.getContext());
//                ad.setMessage(namaEvent.get(position));
//                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                ad.show();
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return namaEvent.size();
//    }
//}
