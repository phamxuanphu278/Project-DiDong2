package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.Users;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseManager DAO;
    Button btnLuu;
    Button btnNhapLai;
    Button btnQuayLai;
    EditText edtUser, edtPassword, edtFullname,edtNumberPhone, edtAdress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DAO = new FirebaseManager(this);
        btnLuu = findViewById(R.id.btnSave);
        btnNhapLai = findViewById(R.id.btnNhapLai);
        btnQuayLai = findViewById(R.id.btnBack);
        edtUser = findViewById(R.id.txtUsername_Login);
        edtPassword = findViewById(R.id.txtPassword_Login);
        edtFullname = findViewById(R.id.txtFullname);
        edtNumberPhone = findViewById(R.id.txtNumberPhone);
        edtAdress = findViewById(R.id.txtAdress);

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
                Users users = createUser();
                if(users != null)
                {
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
                    } else {
                        DAO.registerUser(users, new FirebaseManager.IListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công tài khoản", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                clearForm();
                            }

                            @Override
                            public void onFail() {

                            }
                        });

                    }
                }
            }
        });
    }
    private Users createUser() {
        String username = edtUser.getText().toString();
        String password = edtPassword.getText().toString();
        String fullname = edtFullname.getText().toString();
        String numberphone = edtNumberPhone.getText().toString();
        String address = edtAdress.getText().toString();
        Users users = new Users("",username, password, fullname,address, numberphone);
        return users;
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
