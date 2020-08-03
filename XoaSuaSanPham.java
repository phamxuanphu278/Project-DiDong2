package com.qlbh.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
import com.qlbh.doan.database.DatabaseManager;
import com.qlbh.doan.model.SanPham;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class XoaSuaSanPham extends AppCompatActivity {
    SanPham sanpham;
    EditText edtTen, edtSoluong, edtGia, edtMota;
    ImageView imgAvatar;
    DatabaseManager db;
    Spinner spn;
    String danhmucchon;
    Button btnCamera, btnFolder, btnBack;
    Button btnEdit, btnDelete;
    int i;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoa_sua_sanpham);
        edtTen = findViewById(R.id.edtTenSP);
        edtSoluong = findViewById(R.id.edtSoluongSP);
        edtGia = findViewById(R.id.edtGiaSanPham);
        edtMota = findViewById(R.id.edtMotaSP);
        spn = findViewById(R.id.spinnerDanhMucSP);
        btnCamera = findViewById(R.id.ibnCamera);
        btnFolder = findViewById(R.id.ibnFolder);
        btnEdit = findViewById(R.id.btnEditSP);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        imgAvatar = findViewById(R.id.imgHinh_EditSP);
        db = new DatabaseManager(this);
        getData();
        loadSpinner();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XoaSuaSanPham.this, SanPhamActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                danhmucchon = spn.getSelectedItem().toString();
                db.EditSanPham(i, edtTen.getText().toString(), danhmucchon, Integer.parseInt(edtSoluong.getText().toString()), Integer.parseInt(edtGia.getText().toString()), edtMota.getText().toString(), ConverttoArrayByte(imgAvatar));
                Intent intent = new Intent(XoaSuaSanPham.this, SanPhamActivity.class);
                startActivity(intent);
                Toast.makeText(XoaSuaSanPham.this, "Sửa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(XoaSuaSanPham.this);
                dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                dialog.setMessage("Bạn có muốn xóa sản phẩm này?");
                dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(XoaSuaSanPham.this, SanPhamActivity.class);
                        startActivity(intent);
                        db.DeleteSanPham(i);
                        Toast.makeText(XoaSuaSanPham.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("HỦY", null).show();
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CODE_CAMERA);
            }
        });
        btnFolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, REQUEST_CODE_FOLDER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_CAMERA&&resultCode==RESULT_OK & data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        }
        if (requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK & data!=null){
            Uri uri = data.getData();
            try {
                InputStream ipstream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ipstream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void loadSpinner(){
        List<String> danhmuc  = db.getAllDanhMuc();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, danhmuc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn.setAdapter(dataAdapter);
    }
    public byte[] ConverttoArrayByte(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public void getData(){
        if (getIntent().getExtras()!=null){
            sanpham = (SanPham) getIntent().getSerializableExtra("Edit");
            i = sanpham.getmMaSP();
            byte[] hinhanh = sanpham.getmAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
            imgAvatar.setImageBitmap(bitmap);
            edtTen.setText(sanpham.getmTenSP());
            edtSoluong.setText(String.valueOf(sanpham.getmSoLuong()));
            edtGia.setText(String.valueOf(sanpham.getmGiaban()));
            edtMota.setText(sanpham.getmMota());
        }
    }
}
