package com.qlbh.doan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qlbh.doan.R;
import com.qlbh.doan.model.SanPham;

import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    public SanPhamAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view ==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_sanpham, null);
        }
        SanPham sanpham = getItem(position);
        if (sanpham!=null){
            ImageView imgHinhdaidien = view.findViewById(R.id.imgAnhSP);
            TextView txtTen = view.findViewById(R.id.tv_tensp_list);
            TextView txtSoluong = view.findViewById(R.id.lv_Soluong);
            TextView txtGia = view.findViewById(R.id.tv_giasp_list);
            TextView txtDanhmuc = view.findViewById(R.id.lv_Danhmuc);
            byte[] hinhAnh = sanpham.getmAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            imgHinhdaidien.setImageBitmap(bitmap);
            txtTen.setText(sanpham.getmTenSP());
            txtSoluong.setText(String.valueOf(sanpham.getmSoLuong()));
            txtGia.setText(String.valueOf(sanpham.getmGiaban()));
            txtDanhmuc.setText(sanpham.getmDanhMuc());
        }
        return view;
    }

}
