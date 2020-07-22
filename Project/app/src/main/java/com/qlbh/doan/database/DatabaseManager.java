package com.qlbh.doan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.qlbh.doan.model.DanhMuc;
import com.qlbh.doan.model.HoaDon;
import com.qlbh.doan.model.Users;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    // Khai báo tên & version
    private static String DB_NAME = "QUANLYBANHANG";
    private static int DB_VERSION = 1;
    // Khai báo id
    public static final String ID = "_id";
    // khai báo bảng users
    private static final String TABLE_USER = "users";
    private static final String TABLE_DANHMUC = "danhmuc";
    private static final String TABLE_SANPHAM = "sanpham";
    private static final String TABLE_HOADON = "hoadon";
    // khai báo thuộc tính của bảng users
    private static final String USER_NAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String FULL_NAME = "Fullname";
    private static final String NUMBER_PHONE = "Numberphone";
    private static final String ADRESS = "Adress";
    // Các cột bảng danh mục
    public static final String TEN_DANHMUC = "Tendanhmuc";

    // Các cột bảng sản phẩm
    public static final String TEN_SANPHAM = "Tensanpham";
    public static final String DANH_MUC = "Danhmuc";
    public static final String SO_LUONG = "Soluong";
    public static final String GIA_BAN = "Giaban";
    public static final String MO_TA = "Mota";
    public static final String ANH = "Anh";
    // các cột bảng hóa đơn
    public static final String TEN_KH = "TenKhach";


    // tạo bảng users
    private String SQLQuery_CREATE_TBUSER = "CREATE TABLE " + TABLE_USER
            + " (" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME
            + " TEXT, " + PASSWORD + " TEXT, " + FULL_NAME
            + " TEXT,"+ NUMBER_PHONE + " TEXT, " + ADRESS +" TEXT);";
    // tạo bảng danh mục
    private String SQLQuery_CREATE_DANHMUC = "CREATE TABLE " + TABLE_DANHMUC
            + " (" + ID + " integer primary key, " + TEN_DANHMUC
            + " TEXT)";
    // tạo bảng sản phẩm
    private String SQLQuery_CREATE_TBLSANPHAM = "CREATE TABLE " + TABLE_SANPHAM
            + " (" + ID + " integer primary key, " + TEN_SANPHAM
            + " TEXT UNIQUE, " + DANH_MUC + " TEXT, " + SO_LUONG + " integer, "
            + GIA_BAN + " integer, " + MO_TA
            + " TEXT, " + ANH + " BLOB)";
    private  String SQLQuery_CREATE_TBLHOADON = "CREATE TABLE " + TABLE_HOADON
            + " (" + ID + " integer primary key, " + TEN_SANPHAM
            + " TEXT UNIQUE, " + GIA_BAN + " INTEGER, "+ SO_LUONG + " INTEGER, " + TEN_KH + " TEXT, " + NUMBER_PHONE + " TEXT, "
            + ADRESS + " TEXT)";
    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery_CREATE_TBUSER);
        db.execSQL(SQLQuery_CREATE_DANHMUC);
        db.execSQL(SQLQuery_CREATE_TBLSANPHAM);
        db.execSQL(SQLQuery_CREATE_TBLHOADON);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // hàm đăng ký tài khoản
    public void registerUser(Users users) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, users.getUser());
        values.put(PASSWORD, users.getPassword());
        values.put(FULL_NAME, users.getFullName());
        values.put(NUMBER_PHONE, users.getNumberPhone());
        values.put(ADRESS, users.getAdress());
        database.insert(TABLE_USER, null, values);
        database.close();
    }
    // hàm kiểm tra đăng nhập
    public boolean checklogin(String user, String pass) {
        SQLiteDatabase database = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE username ='"
                + user + "'AND password='" + pass + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() >= 1) {
            database.close();
            return true;
        }
        database.close();
        return false;
    }
    // hàm thêm danh mục
    public void addDanhmuc(DanhMuc dm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_DANHMUC, dm.getTenDM());
        db.insert(TABLE_DANHMUC, null, values);
        db.close();
    }
    // hàm lấy toàn bộ danh mục
    public Cursor getDanhMuc() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM danhmuc;", null);
        return cursor;
    }
    // hàm xóa danh mục
    public void DeleteItem (int i){
        database = this.getWritableDatabase();
        database.delete(TABLE_DANHMUC, ID + " = " + i, null);
        database.close();
    }
    // load danh mục vào Spinner
    public List<String> getAllDanhMuc() {
        List<String> listDanhmuc = new ArrayList<String>();
        String selectQuery = "SELECT * FROM DanhMuc;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                listDanhmuc.add(cursor.getString(1));

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return listDanhmuc;
    }
    // thêm sản phẩm
    public void ThemSanPham(String Ten, String Danhmuc, int Soluong,
                            int Giaban, String Mota, byte[] Hinh) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into SanPham values (null, ?, ?, ?, ?,?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, Ten);
        statement.bindString(2, Danhmuc);
        statement.bindLong(3, Soluong);
        statement.bindLong(4, Giaban);
        statement.bindString(5, Mota);
        statement.bindBlob(6, Hinh);
        statement.executeInsert();
        db.close();
    }
    // hàm lấy toàn bộ sản phẩm
    public Cursor getAllSanPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham;", null);
        return cursor;
    }
    // hàm sửa sản phẩm
    public void EditSanPham(int MaSP, String Ten, String Danhmuc, int Soluong,
                            int Giaban,String Mota, byte[] Hinh){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE SanPham SET Tensanpham = ? , Danhmuc = ? , Soluong = ?, Giaban = ?, Mota = ?, Anh = ? Where _id = ? ";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, Ten);
        statement.bindString(2, Danhmuc);
        statement.bindLong(3, Soluong);
        statement.bindLong(4, Giaban);
        statement.bindString(5, Mota);
        statement.bindBlob(6, Hinh);
        statement.bindLong(7, MaSP);
        statement.execute();
        db.close();
    }
    // hàm xóa sản phẩm
    public void DeleteSanPham (int i) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_SANPHAM, ID + " = " + i, null);
        database.close();
    }
    // hàm hiển thị kho hàng
    public Cursor showKhoHang() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham order by Giaban, Soluong;", null);
        return cursor;
    }
    //hàm tính tổng giá trị sản phẩm trong kho
    public int getTongGiaTriHangHoa() {
        int tongGiaTri = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham order by Giaban, Soluong;", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            tongGiaTri += cursor.getDouble(1);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return tongGiaTri;
    }
    //hàm tính tổng số lượng sản phẩm trong kho
    public int getTongSoLuongSanPham() {
        int tongGiaTri = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham order by Giaban, Soluong;", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            tongGiaTri += cursor.getDouble(2);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return tongGiaTri;
    }
    // hàm sắp xếp số lượng kho hàng giảm dần
    public Cursor sapxepKhoHangTheoSoLuong() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham order by Soluong DESC;", null);
        return cursor;
    }
    // hàm sắp xếp giá sản phẩm tăng dần
    public Cursor sapxepGiaSanPhamTangDan() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham order by Giaban ASC;", null);
        return cursor;
    }
    // hàm tìm kiếm sản phẩm
    public Cursor timKhoHang(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham Where Tensanpham Like '%"+name+"%';", null);
        return cursor;
    }
    // load danh mục vào Spinner
    public Cursor getOneItemDanhMuc(String spnDanhMuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Tensanpham, Giaban, Soluong from SanPham Where Tensanpham = '"+spnDanhMuc+"';", null);
        return cursor;
    }
    // hàm lấy toàn bộ sản phẩm
    public Cursor getAllHomeSanPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham;", null);
        return cursor;
    }
    // hàm hiển thị điện thoại Huawei
    public Cursor getDienThoaiHuawei() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham Where Danhmuc = 'Huawei'", null);
        return cursor;
    }
    // hàm hiển thị điện thoại Iphone
    public Cursor getDienThoaiIphone() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham Where Danhmuc = 'Iphone'", null);
        return cursor;
    }
    // hàm hiển thị điện thoại Samsung
    public Cursor getDienThoaiSamsung() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham Where Danhmuc = 'Samsung'", null);
        return cursor;
    }
    // đặt hàng
    public void addHoaDon(HoaDon hoaDon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEN_SANPHAM, hoaDon.getTenSP());
        values.put(GIA_BAN, hoaDon.getGiaSP());
        values.put(SO_LUONG, hoaDon.getSoLuong());
        values.put(TEN_KH, hoaDon.getTenKH());
        values.put(NUMBER_PHONE, hoaDon.getNumberPhone());
        values.put(ADRESS, hoaDon.getAdress());
        db.insert(TABLE_HOADON, null, values);
        db.close();
    }
    // hàm hiển thị hóa đơn
    public Cursor showHoaDon() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From hoadon", null);
        return cursor;
    }
    // hàm hủy đơn hàng
    public void huyDonHang (int i) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_HOADON, ID + " = " + i, null);
        database.close();
    }
}
