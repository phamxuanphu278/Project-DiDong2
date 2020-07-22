package com.qlbh.doan.model;

public class HoaDon {
    private int maHoaDon;
    private String tenSP;
    private int soLuong;
    private int giaSP;
    private String tenKH;
    private String numberPhone;
    private String adress;

    public HoaDon() {
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public HoaDon(String tenSP, int soLuong, int giaSP, String tenKH, String numberPhone, String adress) {
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaSP = giaSP;
        this.tenKH = tenKH;
        this.numberPhone = numberPhone;
        this.adress = adress;
    }

    public HoaDon(int maHoaDon, String tenSP, int soLuong, int giaSP, String tenKH, String numberPhone, String adress) {
        this.maHoaDon = maHoaDon;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaSP = giaSP;
        this.tenKH = tenKH;
        this.numberPhone = numberPhone;
        this.adress = adress;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
