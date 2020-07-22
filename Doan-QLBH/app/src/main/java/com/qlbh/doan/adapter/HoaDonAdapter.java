package com.qlbh.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qlbh.doan.R;
import com.qlbh.doan.model.HoaDon;

import java.util.List;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    public HoaDonAdapter(@NonNull Context context, int resource, @NonNull List<HoaDon> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_hoadon, null);
        }
        HoaDon hoaDon = getItem(position);
        if (hoaDon!=null){
            TextView txtMaHD = view.findViewById(R.id.txtMaHoaDon);
            TextView txtTenSP = view.findViewById(R.id.txtTenSP);
            TextView txtGiaSP = view.findViewById(R.id.txtGiaSP);
            TextView txtSoluongSP = view.findViewById(R.id.txtSoluong);
            TextView txtTenKH = view.findViewById(R.id.txtTenKH);
            TextView txtSoDT = view.findViewById(R.id.txtSoDT);
            TextView txtDiaChi = view.findViewById(R.id.txtDiaChi);
            TextView txtTongTien = view.findViewById(R.id.txtTongtien);
            txtMaHD.setText("MAHD" + hoaDon.getMaHoaDon());
            txtTenSP.setText(hoaDon.getTenSP());
            txtGiaSP.setText(String.valueOf(hoaDon.getGiaSP()));
            txtSoluongSP.setText(String.valueOf(hoaDon.getSoLuong()));
            txtTenKH.setText(hoaDon.getTenKH());
            txtSoDT.setText(hoaDon.getNumberPhone());
            txtDiaChi.setText(hoaDon.getAdress());
            int A = Integer.parseInt(String.valueOf(hoaDon.getGiaSP()));
            int B = Integer.parseInt(String.valueOf(hoaDon.getSoLuong()));
            txtTongTien.setText(String.valueOf(A * B));
        }
        return view;
    }
}
