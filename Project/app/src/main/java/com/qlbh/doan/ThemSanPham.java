package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.qlbh.doan.database.DatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ThemSanPham extends AppCompatActivity {
    DatabaseManager db;
    Spinner spn;
    Button ibnCam;
    Button ibnFol;
    ImageView imgHinh;
    String danhmucchon;
    Button btnSave;
    Button btnBack;
    EditText edtSoluong;
    EditText edtGiaca;
    EditText edtTenSP;
    EditText edtMotaSP;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sanpham);
        imgHinh = findViewById(R.id.imgHinh);
        btnSave = findViewById(R.id.btnLuu_ThemSP);
        btnBack = findViewById(R.id.btnBack);
        spn = findViewById(R.id.spinnerDM);
        ibnCam = findViewById(R.id.ibnCamera);
        ibnFol = findViewById(R.id.ibnFolder);
        edtSoluong = findViewById(R.id.txtSoluong_ThemSP);
        edtTenSP = findViewById(R.id.txtTenSP_ThemSP);
        edtGiaca = findViewById(R.id.txtGiaca_ThemSP);
        edtMotaSP = findViewById(R.id.txtDescription);
        db = new DatabaseManager(this);
        loadSpinner();
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(edtTenSP.length() == 0){
                    Toast.makeText(ThemSanPham.this, "Nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                }else if(edtGiaca.length() == 0){
                    Toast.makeText(ThemSanPham.this, "Nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                }else if(edtSoluong.length() == 0){
                    Toast.makeText(ThemSanPham.this, "Nhập số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                }else if(edtMotaSP.length() == 0) {
                    Toast.makeText(ThemSanPham.this, "Nhập Mô tả sản phẩm", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    danhmucchon = spn.getSelectedItem().toString();
                    db.ThemSanPham(edtTenSP.getText().toString(), danhmucchon, Integer.parseInt(edtSoluong.getText().toString()), Integer.parseInt(edtGiaca.getText().toString()), edtMotaSP.getText().toString(), ConverttoArrayByte(imgHinh));
                    Toast.makeText(ThemSanPham.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemSanPham.this, SanPhamActivity.class);
                    startActivity(intent);
                    clearForm();
                }

//				}
            }

        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemSanPham.this, SanPhamActivity.class);
                startActivity(intent);
            }
        });
        ibnCam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CODE_CAMERA);

            }
        });
        ibnFol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, REQUEST_CODE_FOLDER);
            }
        });
    }
    public void clearForm(){
        edtTenSP.setText("");
        edtTenSP.requestFocus();
        edtSoluong.setText("");
        edtGiaca.setText("");
        edtMotaSP.setText("");
        imgHinh.setImageResource(R.drawable.image);
    }
    public void loadSpinner(){
        List<String> danhmuc  = db.getAllDanhMuc();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, danhmuc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn.setAdapter(dataAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_CAMERA&&resultCode==RESULT_OK & data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);

        }
        if (requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK & data!=null){
            Uri uri = data.getData();
            try {
                InputStream ipstream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ipstream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public byte[] ConverttoArrayByte(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
