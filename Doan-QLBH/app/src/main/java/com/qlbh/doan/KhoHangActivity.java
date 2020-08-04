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
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class KhoHangActivity extends AppCompatActivity {
    ArrayList<SanPham> arraySanpham = new ArrayList<SanPham>();
    KhoHangAdapter adapter;
    Cursor cursor;
    FirebaseManager dbmanager;
    ListView lv;
    TextView txtTongSoLuong;
    TextView txtTongGiaTriHH;
    EditText edtTimSP;
    Button btnTimSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khohang);
        lv = findViewById(R.id.lv_khohang);
        txtTongSoLuong = findViewById(R.id.txtTongSoLuong);
        txtTongGiaTriHH = findViewById(R.id.txtTongGia);
        edtTimSP = findViewById(R.id.edtTimSP);
        btnTimSP = findViewById(R.id.btnTimSP);
        dbmanager = new FirebaseManager(this);
        displaySP();
        btnTimSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timSanPham();
            }
        });

    }
    public void displaySP(){
        dbmanager.loadSanPham(new FirebaseManager.IListener() {
            @Override
            public void onSuccess() {
                arraySanpham.clear();
                arraySanpham.addAll(dbmanager.getArraySanpham());
                adapter = new KhoHangAdapter(KhoHangActivity.this, R.layout.list_item_khohang, arraySanpham);
                lv.setAdapter(adapter);
                txtTongSoLuong.setText(dbmanager.getTongSoLuongHangHoa()+"");
                txtTongGiaTriHH.setText(dbmanager.getTongGiaTriHangHoa() + "");
            }

            @Override
            public void onFail() {

            }
        });


    }
    public void timSanPham(){
        String keyword = edtTimSP.getText().toString();
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
            adapter = new KhoHangAdapter(this, R.layout.list_item_khohang, arraySanpham);
            lv.setAdapter(adapter);

        }
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
