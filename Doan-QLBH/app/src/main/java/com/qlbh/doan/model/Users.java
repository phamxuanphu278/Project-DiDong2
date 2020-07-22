package com.qlbh.doan.model;

public class Users {
    private String user;
    private String password;
    private String fullName;
    private String adress;
    private String numberPhone;

    public Users(String user, String password, String fullName, String adress, String numberPhone) {
        this.user = user;
        this.password = password;
        this.fullName = fullName;
        this.adress = adress;
        this.numberPhone = numberPhone;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAdress() {
        return adress;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", adress='" + adress + '\'' +
                ", numberPhone=" + numberPhone +
                '}';
    }
}
