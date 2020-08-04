package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qlbh.doan.adapter.HoaDonAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.HoaDon;

import java.util.ArrayList;

public class HoaDonActivity extends AppCompatActivity {
    Button btnBack;
    Cursor cursor;
    ArrayList<HoaDon> arrHoaDon = new ArrayList<HoaDon>();
    HoaDonAdapter adapter;
    ListView lv;
    FirebaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        btnBack = findViewById(R.id.btnBack);
        lv = findViewById(R.id.lv_hoadon);
        dbManager = new FirebaseManager(this);
        display();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoaDonActivity.this, ManagementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(HoaDonActivity.this);
                dialog.setTitle("Chọn kiểu hóa đơn");
                dialog.setMessage("Bạn có chắc chắn chọn kiểu hóa đơn này?");
                dialog.setPositiveButton("GIAO HÀNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(HoaDonActivity.this, "Giao hàng", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("HỦY ĐƠN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dbManager.huyDonHang(arrHoaDon.get(position).getMaHoaDon(), new FirebaseManager.IListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(HoaDonActivity.this, "Đơn hàng bị hủy", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail() {

                            }
                        });

                    }
                }).show();
            }
        });
    }
    public void display(){
        if(adapter==null){
            dbManager.loadHoaDon(new FirebaseManager.IListener() {
                @Override
                public void onSuccess() {
                    arrHoaDon.clear();
                    arrHoaDon.addAll(dbManager.getArrayHoaDon());
                    adapter = new HoaDonAdapter(HoaDonActivity.this, R.layout.list_item_hoadon, arrHoaDon);
                    lv.setAdapter(adapter);
                }

                @Override
                public void onFail() {

                }
            });
        }else{
            adapter.notifyDataSetChanged();
        }
    }
}
