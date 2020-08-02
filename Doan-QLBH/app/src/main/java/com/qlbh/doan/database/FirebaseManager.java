package com.qlbh.doan.database;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qlbh.doan.model.DanhMuc;
import com.qlbh.doan.model.HoaDon;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;

public class FirebaseManager {
    public static final String DANH_MUC = "DANH_MUC";
    public static final String SAN_PHAM = "SAN_PHAM";
    public static final String HOA_DON = "HOA_DON";
    public static final String USER = "USER";
    private Context context;
    DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<DanhMuc> arrDM;
    ArrayList<SanPham> arraySanpham;
    ArrayList<HoaDon> arrayHoaDon;
    private ProgressDialog dialog;
    public FirebaseManager(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Đang tải...");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        arrDM = new ArrayList<>();
        arraySanpham = new ArrayList<>();
        arrayHoaDon = new ArrayList<>();
    }
    public void showLoading(boolean isShow){
        if(dialog != null){
            if(isShow){
                dialog.show();
            }else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }
    }
    public interface IListener{
        void onSuccess();
        void onFail();
    }

    public interface IListenerUploadFile{
        void onSuccess(String url);
        void onFail();
    }
    public void themDanhMuc(DanhMuc danhMuc, final IListener listener){
        showLoading(true);
        String id = mDatabase.child(DANH_MUC).push().getKey();
        danhMuc.setMaDM(id);
        mDatabase.child(DANH_MUC).child(danhMuc.getMaDM()).setValue(danhMuc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showLoading(false);
                listener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                listener.onFail();
            }
        });
    }
    public ArrayList<DanhMuc> getArrDM() {
        return arrDM;
    }
    public void loadDSDanhMuc(final IListener listener){
        showLoading(true);
        mDatabase.child(DANH_MUC).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DanhMuc danhMuc;
                arrDM = new ArrayList<DanhMuc>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    danhMuc = ds.getValue(DanhMuc.class);
                    arrDM.add(danhMuc);
                }
                listener.onSuccess();
                showLoading(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFail();
            }
        });
    }
    public void xoaDanhMuc(String id, final IListener listener){
        showLoading(true);
        mDatabase.child(DANH_MUC).child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onSuccess();
                showLoading(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFail();
            }
        });
    }
}
