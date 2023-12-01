package com.example.quran;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.onurkaganaldemir.ktoastlib.KToast;

public class NoInternet extends AppCompatActivity {
    private Intent intent;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        KToast.errorToast(this, "لا يوجد اتصال بالانترنت !", Gravity.BOTTOM, KToast.LENGTH_AUTO);


        refresh = findViewById(R.id.btn_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork !=null && activeNetwork.isConnectedOrConnecting();
                if (isConnected){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            intent = new Intent(NoInternet.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },20);
                }else {
                    KToast.errorToast(NoInternet.this, "لا يوجد اتصال بالانترنت !", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                }
            }
        });

    }
}