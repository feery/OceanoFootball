package com.fycedwin.oceanofootball.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fycedwin.oceanofootball.R;
import com.fycedwin.oceanofootball.app.MyAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> arrayEvent= new ArrayList<String>();
    private ArrayList<String> nama;
    private ArrayList<String> judul;
    private ArrayList<String>date;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] OCEANOPICTURE= {R.drawable.sladingsatu,R.drawable.sladingdua,R.drawable.sladingtiga};
    private ArrayList<String> BannerArray = new ArrayList<String>();
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_home, container, false);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        nama= new ArrayList<>();
        judul= new ArrayList<>();
        date = new ArrayList<>();
       // Dataset();
//        //recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_home);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new RecyclerViewAdapter2(arrayEvent,nama,judul,date);

        //updateNewsList();

        bannerSet(rootView);

        return rootView;
    }


    private void bannerSet(final View rootView){

        for(int i=0;i<OCEANOPICTURE.length;i++)
            XMENArray.add(OCEANOPICTURE[i]);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getActivity(),XMENArray));
        CircleIndicator indicator = (CircleIndicator) rootView.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == OCEANOPICTURE.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        },10000 ,20000 );

    }
    public void updateNewsList(){
        recyclerView.setAdapter(adapter);
    }

//    public void Dataset(){
//
//        /**
//         * Tambahkan item ke dataset
//         * dalam prakteknya bisa bermacam2
//         * tidak hanya String seperti di kasus ini
//         */
//
////        for(int i=0;i<gambar.length;i++)
////            arrayEvent.add(gambar[i]);
////        nama.add("EVENT");
////        judul.add("4 funthlon Competition");
////        date.add("17 Augustus 2017");
////
////        nama.add("EVENT");
////        judul.add("4 funthlon Competition");
////        date.add("17 Augustus 2017");
////
////        nama.add("EVENT");
////        judul.add("4 funthlon Competition");
////        date.add("17 Augustus 2017");
//
//        String baseurl = "http://klaster.in/api/Articles/Search/";
//        String url= baseurl ;
//
//        nama.clear();
//        pDialog.setMessage("Logging in ...");
//        showDialog();
//        JsonArrayRequest req = new JsonArrayRequest(url,new Response.Listener<JSONArray>(){
//            @Override
//            public void onResponse(JSONArray response) {
//                hideDialog();
//                try{
//                    if(response!= null){
//                        int len = response.length();
//                        for(int i=0;i<len;i++){
//                            JSONObject news= response.getJSONObject(i);
//
//                            arrayEvent.add(news.get("Img").toString());
//                            nama.add(news.get("Title").toString());
//                            judul.add(news.get("Text").toString());
//                            date.add((news.get("Date").toString()));
//
//                        }
//                        updateNewsList();
//                    }
//                    else{
//                        hideDialog();
//                        Toast.makeText(getActivity(), "Null Response", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch(JSONException e){
//                    hideDialog();
//                    Toast.makeText(getActivity(), "JSONException", Toast.LENGTH_SHORT).show();
//                }
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                hideDialog();
//                Toast.makeText(getActivity(), "Request Error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        Volley.newRequestQueue(getActivity()).add(req);
//    }
//
//
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

}
