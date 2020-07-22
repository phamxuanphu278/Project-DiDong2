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
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
                break;
            case R.id.menuIphone:
                break;
            case R.id.menuSamsung:
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
}
