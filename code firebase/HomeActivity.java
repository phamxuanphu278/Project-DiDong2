package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qlbh.doan.adapter.KhoHangAdapter;
import com.qlbh.doan.adapter.SanPhamAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.DanhMuc;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<SanPham> arraySanpham = new ArrayList<SanPham>();
    ArrayList<DanhMuc> danhmuc = new ArrayList<DanhMuc>();
    SanPhamAdapter adapter;
    Cursor cursor;
    FirebaseManager dbmanager;
    ListView lv;
    Spinner spn;
    Button btnTimKiem;
    EditText edtTimSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lv = findViewById(R.id.listView_home);
        spn = findViewById(R.id.spinnerDM);
        btnTimKiem = findViewById(R.id.btnTimSP);
        edtTimSp = findViewById(R.id.edtTimSP);
        dbmanager = new FirebaseManager(this);
        displaySP();
        loadSpinner();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
                                    long arg3) {
                Intent intent = new Intent(HomeActivity.this, ProductInformation.class);
                intent.putExtra("Thongtin", adapter.getItem(postion));
                startActivity(intent);
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timSanPham();
            }
        });
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    displaySP();
                }else {
                    filter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void timSanPham(){
        String keyword = edtTimSp.getText().toString();
        ArrayList<SanPham> temp = new ArrayList<>();
        if(keyword.isEmpty()){
            displaySP();
        }else {
            for (SanPham sp : arraySanpham){
                if(sp.getmTenSP().toLowerCase().trim().contains(keyword.trim().toLowerCase())){
                    temp.add(sp);
                }
            }
            arraySanpham = temp;
            adapter = new SanPhamAdapter(this, R.layout.list_item_sanpham, arraySanpham);
            lv.setAdapter(adapter);

        }
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

    public void displaySP(){
        dbmanager.loadSanPham(new FirebaseManager.IListener() {
            @Override
            public void onSuccess() {
                arraySanpham.clear();
                arraySanpham.addAll(dbmanager.getArraySanpham());
                adapter = new SanPhamAdapter(HomeActivity.this, R.layout.list_item_sanpham, arraySanpham);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFail() {

            }
        });

    }

    public void filter(){
        String keyword = danhmuc.get(spn.getSelectedItemPosition()).getTenDM();
        ArrayList<SanPham> temp = new ArrayList<>();
        if(keyword.isEmpty()){
            displaySP();
        }else {
            for (SanPham sp : arraySanpham){
                if(sp.getmDanhMuc().toLowerCase().trim().equals(keyword.trim().toLowerCase())){
                    temp.add(sp);
                }
            }
            adapter = new SanPhamAdapter(this, R.layout.list_item_sanpham, temp);
            lv.setAdapter(adapter);

        }
    }

    public void loadSpinner(){
        dbmanager.loadDSDanhMuc(new FirebaseManager.IListener() {
            @Override
            public void onSuccess() {
                danhmuc.add(new DanhMuc("Chọn loại danh mục"));
                danhmuc.addAll(dbmanager.getArrDM());
                ArrayAdapter<DanhMuc> dataAdapter = new ArrayAdapter<DanhMuc>(HomeActivity.this, android.R.layout.simple_spinner_item, danhmuc);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spn.setAdapter(dataAdapter);
            }
            @Override
            public void onFail() {

            }
        });
    }
}
