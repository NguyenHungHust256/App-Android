package com.example.ba_hung.findyardgolf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.ba_hung.findyardgolf.R;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_view);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashView.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish(); // destroy current activity..
                startActivity(intent);
            }
        }, 3000);
    }

}
