package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
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
import com.qlbh.doan.database.FirebaseManager;
import com.qlbh.doan.model.DanhMuc;
import com.qlbh.doan.model.SanPham;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ThemSanPham extends AppCompatActivity {
    FirebaseManager db;
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
    Uri imgUri;
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
        db = new FirebaseManager(this);
        loadSpinner();
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                danhmucchon = spn.getSelectedItem().toString();
                SanPham sp = new SanPham();
                sp.setmTenSP(edtTenSP.getText().toString());
                sp.setmDanhMuc(danhmucchon);
                sp.setmSoLuong(Integer.parseInt(edtSoluong.getText().toString()));
                sp.setmGiaban(Integer.parseInt(edtGiaca.getText().toString()));
                sp.setmMota(edtMotaSP.getText().toString());
                sp.setImgURL("");
                db.themSanPham(sp,imgUri, new FirebaseManager.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ThemSanPham.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemSanPham.this, SanPhamActivity.class);
                        startActivity(intent);
                        clearForm();
                    }

                    @Override
                    public void onFail() {

                    }
                });

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
        db.loadDSDanhMuc(new FirebaseManager.IListener() {
            @Override
            public void onSuccess() {
                ArrayList<DanhMuc> danhmuc  = db.getArrDM();
                ArrayAdapter<DanhMuc> dataAdapter = new ArrayAdapter<DanhMuc>(ThemSanPham.this, android.R.layout.simple_spinner_item, danhmuc);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spn.setAdapter(dataAdapter);
            }

            @Override
            public void onFail() {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_CAMERA&&resultCode==RESULT_OK & data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);

        }
        if (requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK & data!=null){
            imgUri = data.getData();
            try {
                InputStream ipstream = getContentResolver().openInputStream(imgUri);
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
