package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qlbh.doan.adapter.SanPhamAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class SamsungActivity extends AppCompatActivity {
    ArrayList<SanPham> arraySanpham = new ArrayList<SanPham>();
    SanPhamAdapter adapter;
    Cursor cursor;
    DatabaseManager dbmanager;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samsung);
        lv = findViewById(R.id.listView_home);
        dbmanager = new DatabaseManager(this);
        displaySP();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(SamsungActivity.this, ProductInformation.class);
                intent.putExtra("Thongtin", adapter.getItem(arg2));
                startActivity(intent);
            }
        });
    }
    public void displaySP(){
        cursor = dbmanager.getDienThoaiSamsung();
        while (cursor.moveToNext()){
            arraySanpham.add(new SanPham(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4),cursor.getString(5), cursor.getBlob(6)));
        }
        adapter = new SanPhamAdapter(this, R.layout.list_item_sanpham, arraySanpham);
        lv.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();
        switch (menuID) {
            case R.id.itemBack:
                itemBack();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void itemBack()
    {
        Intent intent = new Intent(SamsungActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
