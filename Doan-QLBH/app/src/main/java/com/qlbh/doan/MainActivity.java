package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_loading);
        Thread loading = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }
        };
        loading.start();
    }
    protected void onPause() {
        super.onPause();
        finish();
    }
}
