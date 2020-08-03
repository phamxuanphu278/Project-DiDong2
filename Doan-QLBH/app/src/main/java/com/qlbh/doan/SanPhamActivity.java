package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.qlbh.doan.adapter.SanPhamAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.SanPham;
import java.util.ArrayList;

public class SanPhamActivity extends AppCompatActivity {
    Button btnThem;
    Button btnBack;
    ArrayList<SanPham> arraySanpham = new ArrayList<SanPham>();
    SanPhamAdapter adapter;
    Cursor cursor;
    FirebaseManager dbmanager;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        lv = (ListView)findViewById(R.id.lv_sanpham);
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
        dbmanager = new FirebaseManager(this);
        displaySP();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SanPhamActivity.this, ThemSanPham.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SanPhamActivity.this, ManagementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(SanPhamActivity.this, XoaSuaSanPham.class);
                intent.putExtra("Edit", adapter.getItem(arg2));
                startActivity(intent);
            }
        });
    }
    public void displaySP(){
        dbmanager.loadSanPham(new FirebaseManager.IListener() {
            @Override
            public void onSuccess() {
                arraySanpham.addAll(dbmanager.getArraySanpham());
                adapter = new SanPhamAdapter(SanPhamActivity.this, R.layout.list_item_sanpham, arraySanpham);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFail() {

            }
        });


    }
}
