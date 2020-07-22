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
import android.widget.ListView;
import android.widget.Toast;
import com.qlbh.doan.adapter.DanhMucAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.model.DanhMuc;
import java.util.ArrayList;

public class DanhMucActivity extends AppCompatActivity {
    Button btnThem, btnBack;
    Cursor cursor;
    ArrayList<DanhMuc> arrDM = new ArrayList<DanhMuc>();
    DanhMucAdapter adapter;
    ListView lv;
    DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
        lv = findViewById(R.id.lv_danhmuc);
        dbManager = new DatabaseManager(this);
        display();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhMucActivity.this, ManagementActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhMucActivity.this, ThemDanhMuc.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DanhMucActivity.this);
                dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                dialog.setMessage("Bạn có muốn xóa sản phẩm này?");
                dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(DanhMucActivity.this, DanhMucActivity.class);
                        startActivity(intent);
                        dbManager.DeleteItem(arrDM.get(position).getMaDM());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(DanhMucActivity.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("HỦY", null).show();
            }
        });


    }
    public void display(){
        cursor = dbManager.getDanhMuc();
        if(adapter==null){
            while (cursor.moveToNext()){
                arrDM.add(new DanhMuc(cursor.getInt(0), cursor.getString(1)));
            }
            adapter = new DanhMucAdapter(this, R.layout.list_item_danhmuc, arrDM);
            lv.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
}
