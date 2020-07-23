package com.qlbh.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qlbh.doan.R;
import com.qlbh.doan.model.SanPham;

import java.util.List;

public class KhoHangAdapter extends ArrayAdapter<SanPham> {

    public KhoHangAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_khohang, null);
        }
        SanPham sanpham = getItem(position);
        if (sanpham!=null){
            TextView txtTenSP = view.findViewById(R.id.txtTenSP);
            TextView txtGiaSP = view.findViewById(R.id.txtGiaSP);
            TextView txtSoluongSP = view.findViewById(R.id.txtSoluong);
            txtTenSP.setText(sanpham.getmTenSP());
            txtGiaSP.setText(String.valueOf(sanpham.getmGiaban()));
            txtSoluongSP.setText(String.valueOf(sanpham.getmSoLuong()));
        }
        return view;
    }
}
