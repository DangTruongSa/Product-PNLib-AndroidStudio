package com.example.mob2041_v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_v2.database.Dbhelper;
import com.example.mob2041_v2.model.ModelNguoidung;

public class NguoidungDAO {

    private Dbhelper dbhelper;
    private SharedPreferences sharedPreferences;
    public NguoidungDAO(Context c){
        dbhelper=new Dbhelper(c);
        sharedPreferences = c.getSharedPreferences("dataUser",Context.MODE_PRIVATE);
    }

    public boolean checklogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?", new String[]{username,password});

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("role",cursor.getInt(6));
            editor.apply();
        }

        return cursor.getCount() > 0;
    }

    //signup
    public boolean signupaccount(ModelNguoidung modelNguoidung){
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tennd",modelNguoidung.getTennd());
        contentValues.put("sdt",modelNguoidung.getSdt());
        contentValues.put("diachi",modelNguoidung.getDiachi());
        contentValues.put("tendangnhap",modelNguoidung.getTendangnhap());
        contentValues.put("matkhau",modelNguoidung.getMatkhau());
        contentValues.put("role",1);

        long check = sqLiteDatabase.insert("NGUOIDUNG",null,contentValues);
        return check != -1;
    }

}
