package com.kratos47mhs.ganjoor.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kratos47mhs.ganjoor.R;

public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash_screen);
        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this.getApplicationContext(), GanjorActivity.class));
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
