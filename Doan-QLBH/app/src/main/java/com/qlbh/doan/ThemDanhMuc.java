package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.DanhMuc;

public class ThemDanhMuc extends AppCompatActivity {
    FirebaseManager database;
    EditText edtThemDM;
    Button btnLuu;
    Button btnQuayLai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);
        database = new FirebaseManager(this);
        edtThemDM = findViewById(R.id.edtThemDanhMuc);
        btnLuu = findViewById(R.id.btnLuu);
        btnQuayLai = findViewById(R.id.btnBack);

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
                    database.themDanhMuc(createDanhMuc(), new FirebaseManager.IListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ThemDanhMuc.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ThemDanhMuc.this, DanhMucActivity.class);
                            startActivity(intent);
                            edtThemDM.setText("");
                            edtThemDM.requestFocus();
                        }

                        @Override
                        public void onFail() {

                        }
                    });

                }

            }
        });

    }

    private DanhMuc createDanhMuc() {
        String tenDM = edtThemDM.getText().toString();
        DanhMuc dm = new DanhMuc(tenDM);
        return dm;
    }
}
