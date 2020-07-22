package com.qlbh.doan.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int mMaSP;
    private String mTenSP;
    private String mDanhMuc;
    private int mSoLuong;
    private int mGiaban;
    private  String mMota;
    private byte[] mAnh;
    public SanPham() {
        super();
    }
    public SanPham(String mTenSP,int mGiaban, int mSoLuong) {
        super();
        this.mTenSP = mTenSP;
        this.mGiaban = mGiaban;
        this.mSoLuong = mSoLuong;
    }

    public SanPham(int mMaSP, String mTenSP, String mDanhMuc, int mSoLuong, int mGiaban, String mMota, byte[] mAnh) {
        this.mMaSP = mMaSP;
        this.mTenSP = mTenSP;
        this.mDanhMuc = mDanhMuc;
        this.mSoLuong = mSoLuong;
        this.mGiaban = mGiaban;
        this.mMota = mMota;
        this.mAnh = mAnh;
    }
    public SanPham(int mMaSP, String mTenSP, String mDanhMuc, int mSoLuong,
                   int mGiaban, byte[] mAnh) {
        super();
        this.mMaSP = mMaSP;
        this.mTenSP = mTenSP;
        this.mDanhMuc = mDanhMuc;
        this.mSoLuong = mSoLuong;
        this.mGiaban = mGiaban;
        this.mAnh = mAnh;
    }

    public int getmMaSP() {
        return mMaSP;
    }

    public void setmMaSP(int mMaSP) {
        this.mMaSP = mMaSP;
    }

    public String getmTenSP() {
        return mTenSP;
    }

    public void setmTenSP(String mTenSP) {
        this.mTenSP = mTenSP;
    }

    public String getmDanhMuc() {
        return mDanhMuc;
    }

    public void setmDanhMuc(String mDanhMuc) {
        this.mDanhMuc = mDanhMuc;
    }

    public int getmSoLuong() {
        return mSoLuong;
    }

    public void setmSoLuong(int mSoLuong) {
        this.mSoLuong = mSoLuong;
    }

    public int getmGiaban() {
        return mGiaban;
    }

    public void setmGiaban(int mGiaban) {
        this.mGiaban = mGiaban;
    }

    public String getmMota() {
        return mMota;
    }

    public void setmMota(String mMota) {
        this.mMota = mMota;
    }

    public byte[] getmAnh() {
        return mAnh;
    }

    public void setmAnh(byte[] mAnh) {
        this.mAnh = mAnh;
    }
}
