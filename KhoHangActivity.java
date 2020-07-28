package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.qlbh.doan.adapter.KhoHangAdapter;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class KhoHangActivity extends AppCompatActivity {
    ArrayList<SanPham> arraySanpham = new ArrayList<SanPham>();
    KhoHangAdapter adapter;
    Cursor cursor;
    DatabaseManager dbmanager;
    ListView lv;
    TextView txtTongSoLuong;
    TextView txtTongGiaTriHH;
    EditText edtTimSP;
    Button btnTimSP;
    Button btnSapXepTheoGia;
    Button btnSapxepTheoSoLuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khohang);
        lv = findViewById(R.id.lv_khohang);
        txtTongSoLuong = findViewById(R.id.txtTongSoLuong);
        txtTongGiaTriHH = findViewById(R.id.txtTongGia);
        btnSapXepTheoGia = findViewById(R.id.btnSapXepTheoGia);
        btnSapxepTheoSoLuong = findViewById(R.id.btnsapXepSoluong);
        edtTimSP = findViewById(R.id.edtTimSP);
        btnTimSP = findViewById(R.id.btnTimSP);
        dbmanager = new DatabaseManager(this);
        displaySP();
        btnSapxepTheoSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraySanpham.clear();
                sapXepSoLuongKhoHangGiamDan();
            }
        });
        btnSapXepTheoGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraySanpham.clear();
                sapXepGiaSanPhamTangDan();
            }
        });
        btnTimSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraySanpham.clear();
                timSanPham();
            }
        });
        txtTongGiaTriHH.setText(dbmanager.getTongGiaTriHangHoa() + "");
        txtTongSoLuong.setText(dbmanager.getTongSoLuongSanPham() +"");
    }
    public void displaySP(){
        cursor = dbmanager.showKhoHang();
        while (cursor.moveToNext()){
            arraySanpham.add(new SanPham(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
        }
        adapter = new KhoHangAdapter(this, R.layout.list_item_khohang, arraySanpham);
        lv.setAdapter(adapter);
    }
    public void sapXepSoLuongKhoHangGiamDan(){
        cursor = dbmanager.sapxepKhoHangTheoSoLuong();
        while (cursor.moveToNext()){
            arraySanpham.add(new SanPham(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
        }
        adapter = new KhoHangAdapter(this, R.layout.list_item_khohang, arraySanpham);
        lv.setAdapter(adapter);
    }
    public void sapXepGiaSanPhamTangDan(){
        cursor = dbmanager.sapxepGiaSanPhamTangDan();
        while (cursor.moveToNext()){
            arraySanpham.add(new SanPham(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
        }
        adapter = new KhoHangAdapter(this, R.layout.list_item_khohang, arraySanpham);
        lv.setAdapter(adapter);
    }
    public void timSanPham(){
        cursor = dbmanager.timKhoHang(edtTimSP.getText().toString());
        while (cursor.moveToNext()){
            arraySanpham.add(new SanPham(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
        }
        adapter = new KhoHangAdapter(this, R.layout.list_item_khohang, arraySanpham);
        lv.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_khohang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();
        switch (menuID) {
            case R.id.itemBack:
                backManagement();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void backManagement()
    {
        Intent intent = new Intent(KhoHangActivity.this, ManagementActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
