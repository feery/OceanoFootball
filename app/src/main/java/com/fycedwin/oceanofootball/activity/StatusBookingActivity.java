package com.fycedwin.oceanofootball.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fycedwin.oceanofootball.R;

public class StatusBookingActivity extends AppCompatActivity {
    private RelativeLayout not;
    private LinearLayout yes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_booking);

        not=(RelativeLayout) findViewById(R.id.not_booking);
        yes=(LinearLayout)findViewById(R.id.booking_now);

        yes.setVisibility(View.GONE);
        not.setVisibility(View.VISIBLE);
    }
}
