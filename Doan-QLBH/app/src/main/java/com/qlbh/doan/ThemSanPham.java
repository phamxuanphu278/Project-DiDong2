package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ThemSanPham extends AppCompatActivity {
    Button ibnCam;
    Button ibnFol;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sanpham);
        ibnCam = findViewById(R.id.ibnCamera);
        ibnFol = findViewById(R.id.ibnFolder);
        ibnCam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CODE_CAMERA);

            }
        });
        ibnFol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, REQUEST_CODE_FOLDER);
            }
        });
    }
}
