package com.example.mob2041_v2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper(Context c){
        super(c,"quanlythuvien",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tLoaisach="CREATE TABLE LOAISACH(maloai INTEGER PRIMARY KEY AUTOINCREMENT, tenloai TEXT)";
        db.execSQL(tLoaisach);

        String tTensach="CREATE TABLE SACH(masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT,tacgia TEXT, giaban INTEGER, maloai INTEGER REFERENCES LOAISACH(maloai))";
        db.execSQL(tTensach);

        /*
        role:
        * 1-nguoi dung
        * 2-thu thu
        * 3-admin
        */

        String tNguoidung="CREATE TABLE NGUOIDUNG(mand INTEGER PRIMARY KEY AUTOINCREMENT, tennd TEXT, sdt TEXT, diachi TEXT, tendangnhap TEXT, matkhau TEXT, role INTEGER)";
        db.execSQL(tNguoidung);

        String tPhieumuon="CREATE TABLE PHIEUMUON(mapm INTEGER PRIMARY KEY AUTOINCREMENT, ngaymuon TEXT, ngaytra TEXT, mand INTEGER REFERENCES NGUOIDUNG(mand), masach INTERGER REFERENCES SACH(masach))";
        db.execSQL(tPhieumuon);

        String tCTPM="CREATE TABLE CTPM(mactpm INTEGER PRIMARY KEY, mapm INTEGER REFERENCES PHIEUMUON(mapm), mand INTEGER REFERENCES NGUOIDUNG(mand), soluong INTEGER)";
        db.execSQL(tCTPM);

        //data mau

        db.execSQL("INSERT INTO LOAISACH values(1,'Thieunhi'),(2,'Tinh cam'),(3,'Hanh dong')");
        db.execSQL("INSERT INTO SACH values(1,'Tay du ki','Ngo Thua An',5000,1),(2,'Ke cho em nghe','chua biet',6000,2),(3,'Tay son hao kiet','chua biet luon',4000,3)");
        db.execSQL("INSERT INTO NGUOIDUNG values(1,'Sa','0123451212','Ninh Thuan','sadtps','sa1234',3),(2,'Bao','0123451231','TP.HCM','baodtps','bao1234',2),(3, 'Lê Văn Hùng', '082318912','Q6 TP.HCM', 'hunglv01', '12341234', 3),(4,'Sa','01234512212','Ninh Thuan','1','1',3)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES(1, '20/09/2023', '26/09/2023', 1,1)");
        db.execSQL("INSERT INTO CTPM VALUES(1, 1, 1, 2),(2, 1, 2, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!=newVersion){
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS CTPM");
            onCreate(db);
        }
    }
}