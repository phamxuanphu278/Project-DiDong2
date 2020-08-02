package com.qlbh.doan.database;

import android.app.ProgressDialog;
import android.content.Context;
<<<<<<< HEAD
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
=======

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
>>>>>>> 31f553b1dfe5f3824e2a0ef8bf125df90f3a3099
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
<<<<<<< HEAD
import com.google.firebase.storage.UploadTask;
=======
>>>>>>> 31f553b1dfe5f3824e2a0ef8bf125df90f3a3099
import com.qlbh.doan.model.DanhMuc;
import com.qlbh.doan.model.HoaDon;
import com.qlbh.doan.model.SanPham;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.UUID;
=======
>>>>>>> 31f553b1dfe5f3824e2a0ef8bf125df90f3a3099

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
<<<<<<< HEAD

    public ArrayList<DanhMuc> getArrDM() {
        return arrDM;
    }

    public void setArrDM(ArrayList<DanhMuc> arrDM) {
        this.arrDM = arrDM;
    }

    public ArrayList<SanPham> getArraySanpham() {
        return arraySanpham;
    }

    public void setArraySanpham(ArrayList<SanPham> arraySanpham) {
        this.arraySanpham = arraySanpham;
    }

    public ArrayList<HoaDon> getArrayHoaDon() {
        return arrayHoaDon;
    }

    public void setArrayHoaDon(ArrayList<HoaDon> arrayHoaDon) {
        this.arrayHoaDon = arrayHoaDon;
    }

=======
>>>>>>> 31f553b1dfe5f3824e2a0ef8bf125df90f3a3099
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
<<<<<<< HEAD
=======
    public ArrayList<DanhMuc> getArrDM() {
        return arrDM;
    }
>>>>>>> 31f553b1dfe5f3824e2a0ef8bf125df90f3a3099
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
<<<<<<< HEAD
    public void uploadFile(Uri uri, final IListenerUploadFile listener){
        showLoading(true);
        final StorageReference reference = storageReference.child("Anh/" + UUID.randomUUID().toString());
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // getting image uri and converting into string
                        Uri downloadUrl = uri;
                        String imgURL = downloadUrl.toString();
                        listener.onSuccess(imgURL);
                    }
                });
                showLoading(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFail();
            }
        });
    }

    public void themSanPham(final SanPham sp, Uri uriImg, final IListener listener){
        showLoading(true);
        uploadFile(uriImg, new IListenerUploadFile() {
            @Override
            public void onSuccess(String url) {
                String id = mDatabase.child(SAN_PHAM).push().getKey();
                sp.setmMaSP(id);
                sp.setImgURL(url);
                mDatabase.child(SAN_PHAM).child(sp.getmMaSP()).setValue(sp).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        listener.onFail();
                    }
                });
                showLoading(false);
            }

            @Override
            public void onFail() {

            }
        });
    }
    public void updateSanPham(String id, SanPham sanPham, final IListener listener){
        showLoading(true);
        mDatabase.child(SAN_PHAM).child(id).setValue(sanPham).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
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

    public void loadSanPham(final IListener listener){
        showLoading(true);
        arraySanpham.clear();
        mDatabase.child(SAN_PHAM).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SanPham sanPham;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    sanPham = ds.getValue(SanPham.class);
                    arraySanpham.add(sanPham);
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
=======
>>>>>>> 31f553b1dfe5f3824e2a0ef8bf125df90f3a3099
}
