package com.qlbh.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.qlbh.doan.adapter.DanhMucAdapter;
import com.qlbh.doan.model.DanhMuc;
import java.util.ArrayList;

public class DanhMucActivity extends AppCompatActivity {
    Button btnThem, btnBack;
    ListView lv;
    DatabaseReference reff;
    ArrayList<DanhMuc> danhMuc = new ArrayList<DanhMuc>();
    DanhMucAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
        lv = findViewById(R.id.lv_danhmuc);
        reff = FirebaseDatabase.getInstance().getReference("DanhMuc");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    DanhMuc dm = snap.getValue(DanhMuc.class);
                    danhMuc.add(dm);
                }
                adapter = new DanhMucAdapter(DanhMucActivity.this,android.R.layout.simple_list_item_1, danhMuc);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DanhMucActivity.this, "Connect is success!!", Toast.LENGTH_SHORT).show();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(DanhMucActivity.this);
                        dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                        dialog.setMessage("Bạn có muốn xóa sản phẩm này?");
                        dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).setNegativeButton("HỦY", null).show();
                    }
                });
            }
        });
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
    }
}
