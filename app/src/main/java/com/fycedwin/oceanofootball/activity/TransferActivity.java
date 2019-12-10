package com.fycedwin.oceanofootball.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fycedwin.oceanofootball.R;

import java.util.concurrent.TimeUnit;

public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        final TextView textic = (TextView) findViewById(R.id.afk_hitung);


        hitungMundur(textic);
    }

    private CountDownTimer hitungMundur(final TextView textic){

            CountDownTimer countDownTimer = new CountDownTimer(21600000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                textic.setText(""+String.format("%d: %d : %d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)-60*5,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                textic.setText("Time Up , You Not Transfer");
            }
        }.start();

        return countDownTimer;
    }

}
