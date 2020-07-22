package com.qlbh.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qlbh.doan.R;
import com.qlbh.doan.model.DanhMuc;

import java.util.List;

public class DanhMucAdapter extends ArrayAdapter<DanhMuc> {
    public DanhMucAdapter(@NonNull Context context, int resource, @NonNull List<DanhMuc> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view ==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_danhmuc, null);
        }
        DanhMuc dm = getItem(position);
        if(dm!=null){
            TextView txtTen = view.findViewById(R.id.tvTen);
            TextView txtId = view.findViewById(R.id.tvId);
            txtTen.setText(dm.getTenDM());
            txtId.setText(String.valueOf(dm.getMaDM()));
        }
        return view;
    }
}
