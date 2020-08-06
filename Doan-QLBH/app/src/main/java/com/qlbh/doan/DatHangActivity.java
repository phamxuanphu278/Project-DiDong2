package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.DanhMuc;
import com.qlbh.doan.model.HoaDon;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class DatHangActivity extends AppCompatActivity {
    Button btnCancel;
    Button btnOrder;
    EditText edtTenSP;
    EditText edtGiaSP;
    EditText edtSoluong;
    EditText edtTenKH;
    EditText edtSoDT;
    EditText edtDiaChi;
    SanPham sanpham;
    FirebaseManager database;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        btnCancel = findViewById(R.id.btnCancel);
        edtTenSP = findViewById(R.id.edtTenSP);
        edtGiaSP = findViewById(R.id.edtGiaSP);
        edtSoluong = findViewById(R.id.edtSoLuong);
        edtTenKH = findViewById(R.id.edtTenKH);
        edtSoDT = findViewById(R.id.edtSoDT);
        edtDiaChi = findViewById(R.id.edtDiachi);
        btnOrder = findViewById(R.id.btnOrder);
        database = new FirebaseManager(this);
        getData();
        edtSoluong.setText(String.valueOf(1));
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluong = Integer.parseInt(String.valueOf(edtSoluong.getText()));
                if(soluong < 1 || edtSoluong.length() == 0) {
                    Toast.makeText(DatHangActivity.this, "Vui lòng nhập số lượng >= 1", Toast.LENGTH_SHORT).show();
                }else if(edtTenKH.length() == 0){
                    Toast.makeText(DatHangActivity.this, "Nhập tên của bạn", Toast.LENGTH_SHORT).show();
                }else if(edtSoDT.length() == 0){
                    Toast.makeText(DatHangActivity.this, "Nhập số điện thoại của bạn", Toast.LENGTH_SHORT).show();
                }else if(edtDiaChi.length() == 0){
                    Toast.makeText(DatHangActivity.this, "Nhập địa chỉ của bạn", Toast.LENGTH_SHORT).show();
                }else {
                    database.themHoaDon(datHang(), new FirebaseManager.IListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(DatHangActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DatHangActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFail() {

                        }
                    });

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatHangActivity.this, ProductInformation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }
    public void getData(){
        sanpham = (SanPham) getIntent().getSerializableExtra("dathang");
//        i = sanpham.getmMaSP();
        edtTenSP.setText(sanpham.getmTenSP());
        edtGiaSP.setText(String.valueOf(sanpham.getmGiaban()));
    }

    private HoaDon datHang(){
        String tenSP = edtTenSP.getText().toString();
        int giaSP = Integer.parseInt(edtGiaSP.getText().toString());
        int soLuong = Integer.parseInt(edtSoluong.getText().toString());
        String tenKH = edtTenKH.getText().toString();
        String soDT = edtSoDT.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        HoaDon hoaDon = new HoaDon(tenSP, giaSP, soLuong, tenKH, soDT, diaChi);
        return hoaDon;
    }
}
