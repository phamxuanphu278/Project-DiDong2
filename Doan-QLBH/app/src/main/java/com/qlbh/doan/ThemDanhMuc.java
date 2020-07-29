package com.qlbh.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qlbh.doan.model.DanhMuc;

public class ThemDanhMuc extends AppCompatActivity {
    EditText edtThemDM, edtMaDanhMuc;
    Button btnLuu;
    Button btnQuayLai;
    DatabaseReference reff;
    DanhMuc danhMuc;
    long id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);
        edtThemDM = findViewById(R.id.edtThemDanhMuc);
        edtMaDanhMuc = findViewById(R.id.edtMaDanhMuc);
        btnLuu = findViewById(R.id.btnLuu);
        btnQuayLai = findViewById(R.id.btnBack);
        reff = FirebaseDatabase.getInstance().getReference().child("DanhMuc");
        danhMuc = new DanhMuc();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id = snapshot.getChildrenCount();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemDanhMuc.this, DanhMucActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtThemDM.length() == 0){
                    Toast.makeText(ThemDanhMuc.this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                }else {
                    danhMuc.setMaDM(Integer.parseInt(edtMaDanhMuc.getText().toString().trim()));
                    danhMuc.setTenDM(edtThemDM.getText().toString().trim());

                    reff.child(String.valueOf(id+1)).setValue(danhMuc);
                    Toast.makeText(ThemDanhMuc.this, "Đăng thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemDanhMuc.this, DanhMucActivity.class);
                    startActivity(intent);
                    edtThemDM.setText("");
                    edtMaDanhMuc.setText("");
                }
            }
        });
    }
}
