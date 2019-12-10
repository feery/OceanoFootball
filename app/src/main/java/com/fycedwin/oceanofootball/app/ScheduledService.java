package com.fycedwin.oceanofootball.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fycedwin.oceanofootball.fragment.ScheduleFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Feery on 10/6/2017.
 */

public class ScheduledService extends Service {
    private Timer timer = new Timer();
    private Date currentTime = Calendar.getInstance().getTime();
    private SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");
    private String cvb_date = timeformat.format(currentTime);
    private ScheduleFragment sendRequestToServer;


    private String TOKEN ="a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequestToServer= new ScheduleFragment();
                sendRequestToServer.initDataSet(cvb_date,TOKEN);
                Log.e("Server","Server load 30 second");
            }
        }, 0,30000);//1 milisecon
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
