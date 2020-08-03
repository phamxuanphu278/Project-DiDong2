package com.qlbh.doan.model;

public class DanhMuc {
    private String maDM;
    private String tenDM;

    public DanhMuc() {
    }

    public DanhMuc(String tenDM) {
        super();
        this.tenDM = tenDM;
    }
    public DanhMuc(String maDM, String tenDM) {
        super();
        this.maDM = maDM;
        this.tenDM = tenDM;
    }
    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    @Override
    public String toString() {
        return tenDM;
    }
}
