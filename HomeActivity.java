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
import android.widget.Toast;

import com.qlbh.doan.adapter.SanPhamAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<SanPham> arraySanpham = new ArrayList<SanPham>();
    SanPhamAdapter adapter;
    Cursor cursor;
    DatabaseManager dbmanager;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lv = findViewById(R.id.listView_home);
        dbmanager = new DatabaseManager(this);
        displaySP();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
                                    long arg3) {
                Intent intent = new Intent(HomeActivity.this, ProductInformation.class);
                intent.putExtra("Thongtin", adapter.getItem(postion));
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();
        switch (menuID) {
            case R.id.menuLogout:
                logout();
                break;
            case R.id.menuHuawei:
                huawei();
                break;
            case R.id.menuIphone:
                iphone();
                break;
            case R.id.menuSamsung:
                samsung();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout()
    {
        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
    }
    private void huawei()
    {
        Intent intent = new Intent(HomeActivity.this,HuaweiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        Toast.makeText(this, "Điện thoại Huawei", Toast.LENGTH_SHORT).show();
    }
    private void iphone()
    {
        Intent intent = new Intent(HomeActivity.this,IphoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        Toast.makeText(this, "Điện thoại Iphone", Toast.LENGTH_SHORT).show();
    }
    private void samsung()
    {
        Intent intent = new Intent(HomeActivity.this,SamsungActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        Toast.makeText(this, "Điện thoại Samsung", Toast.LENGTH_SHORT).show();
    }
    public void displaySP(){
        cursor = dbmanager.getAllHomeSanPham();
        while (cursor.moveToNext()){
            arraySanpham.add(new SanPham(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4),cursor.getString(5), cursor.getBlob(6)));
        }
        adapter = new SanPhamAdapter(this, R.layout.list_item_sanpham, arraySanpham);
        lv.setAdapter(adapter);

    }
}
