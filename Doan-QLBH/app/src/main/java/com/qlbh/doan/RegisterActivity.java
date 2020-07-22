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
import com.qlbh.doan.model.Users;

public class RegisterActivity extends AppCompatActivity {
    Button btnLuu;
    Button btnNhapLai;
    Button btnQuayLai;
    EditText edtUser, edtPassword, edtFullname,edtNumberPhone, edtAdress;
    DatabaseReference reff;
    Users user;
    long id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnLuu = findViewById(R.id.btnSave);
        btnNhapLai = findViewById(R.id.btnNhapLai);
        btnQuayLai = findViewById(R.id.btnBack);
        edtUser = findViewById(R.id.txtUsername_Login);
        edtPassword = findViewById(R.id.txtPassword_Login);
        edtFullname = findViewById(R.id.txtFullname);
        edtNumberPhone = findViewById(R.id.txtNumberPhone);
        edtAdress = findViewById(R.id.txtAdress);
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        user = new Users();
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
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUser.length() == 0)
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Username", Toast.LENGTH_SHORT).show();
                }else if(edtPassword.length() == 0)
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                }else if(edtFullname.length() == 0)
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Tên", Toast.LENGTH_SHORT).show();
                }else if(edtNumberPhone.length() == 0)
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Số điện thoại", Toast.LENGTH_SHORT).show();
                } else if(edtAdress.length() == 0)
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Địa chỉ", Toast.LENGTH_SHORT).show();
                }else if(edtUser.length() < 5 || edtPassword.length() < 5 || edtFullname.length() < 5 || edtNumberPhone.length() < 5 || edtAdress.length() < 5)
                {
                    Toast.makeText(RegisterActivity.this, "Điền ít nhất 5 ký tự cho tất cả các trường!!!", Toast.LENGTH_SHORT).show();
                }else {
                    user.setUser(edtUser.getText().toString().trim());
                    user.setPassword(edtPassword.getText().toString().trim());
                    user.setFullName(edtFullname.getText().toString().trim());
                    user.setAdress(edtAdress.getText().toString().trim());
                    user.setNumberPhone(edtNumberPhone.getText().toString().trim());

                    reff.child(String.valueOf(id+1)).setValue(user);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    clearForm();
                }
            }
        });
    }
    private void clearForm() {
        edtUser.setText("");
        edtUser.requestFocus();
        edtPassword.setText("");
        edtNumberPhone.setText("");
        edtFullname.setText("");
        edtAdress.setText("");
    }
}
