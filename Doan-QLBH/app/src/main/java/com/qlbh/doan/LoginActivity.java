package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.database.FirebaseManager;

public class LoginActivity extends AppCompatActivity {
    Button btnDangNhap;
    Button btnDangKy;
    EditText edtUsername;
    EditText edtPassword;
    private FirebaseManager DAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DAO = new FirebaseManager(this);
        btnDangNhap = findViewById(R.id.btnLogin);
        btnDangKy = findViewById(R.id.btnRegister);
        edtUsername = findViewById(R.id.txtUsername_Login);
        edtPassword = findViewById(R.id.txtPassword_Login);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtUsername.getText().toString();
                String matkhau = edtPassword.getText().toString();

                if(taikhoan.length() == 0){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập Username",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                else if(matkhau.length() == 0){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập Password",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                else if(taikhoan.length() < 5 || matkhau.length() <5){
                    Toast.makeText(LoginActivity.this, "Nhập username và password ít nhất 5 ký tự!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                else if(taikhoan.equals("admin") && matkhau.equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, ManagementActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    clearForm();
                    return;
                }
                else {
                    DAO.checkLogin(taikhoan, matkhau, new FirebaseManager.IListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            clearForm();
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng, hãy kiểm tra lại.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
    private void clearForm() {
        edtUsername.setText("");
        edtUsername.requestFocus();
        edtPassword.setText("");
    }
}
