package com.fycedwin.oceanofootball.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fycedwin.oceanofootball.R;

public class ProfilSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_setting);
    }
    public void backHome(View view)
    {
        super.onBackPressed();
    }
}
