package com.example.quran;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity  {
    private  Intent i;
    private ProgressBar progressBar;
    private Button btn_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.idPBLoading);
        btn_load = findViewById(R.id.btn_load);

        ConnectivityManager cm  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork !=null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    btn_load.setBackgroundColor(Color.parseColor("#FF05C46B"));
                    btn_load.setEnabled(true);
                }
            }, 2000);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    i = new Intent(LoadingActivity.this, NoInternet.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);
        }

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}