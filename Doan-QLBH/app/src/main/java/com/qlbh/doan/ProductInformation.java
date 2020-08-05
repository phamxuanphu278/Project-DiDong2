package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;


public class ProductInformation extends AppCompatActivity {
    SanPham sanpham;
    TextView edtTen, edtMota, edtGia;
    ImageView imgAvatar;
    Button btnBack;
    Button btnOrder;
    TextView spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_information);
        edtTen = findViewById(R.id.edtTenSP);
        edtGia = findViewById(R.id.edtGiaSanPham);
        edtMota = findViewById(R.id.edtMotaSP);
        spn = findViewById(R.id.spinnerDanhMucSP);
        imgAvatar = findViewById(R.id.imgHinh_EditSP);
        btnBack = findViewById(R.id.btnBack);
        btnOrder = findViewById(R.id.btnShopping);
        getData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductInformation.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductInformation.this, DatHangActivity.class);
                intent.putExtra("dathang", getIntent().getSerializableExtra("Thongtin"));
                startActivity(intent);
            }
        });
    }

    public void getData(){
        if (getIntent().getExtras()!=null){
            sanpham = (SanPham) getIntent().getSerializableExtra("Thongtin");
            Glide.with(this).load(sanpham.getImgURL()).into(imgAvatar);
            edtTen.setText(sanpham.getmTenSP());
            spn.setText(sanpham.getmDanhMuc());
            edtGia.setText(String.valueOf(sanpham.getmGiaban()) + "$");
            edtMota.setText(sanpham.getmMota());
        }
    }
}
