package com.example.mob2041_v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_v2.database.Dbhelper;
import com.example.mob2041_v2.model.ModelLoaisach;

import java.util.ArrayList;

public class LoaisachDAO {
    private Dbhelper dbhelper;
    public LoaisachDAO(Context c){
        dbhelper=new Dbhelper(c);
    }

    //lay danh sach laoi sach

    public ArrayList<ModelLoaisach> getdslaoisach(){
        ArrayList<ModelLoaisach> list=new ArrayList<ModelLoaisach>();
        SQLiteDatabase sqLiteDatabase=dbhelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH",null);

        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new ModelLoaisach(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themlaoisach(String tenloai){
        SQLiteDatabase sqLiteDatabase =dbhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",tenloai);

        long check=sqLiteDatabase.insert("LOAISACH",null,contentValues);
        return check!=-1;

    }

    public boolean sualoaisach(ModelLoaisach modelLoaisach){
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",modelLoaisach.getTenloai());

        int check=sqLiteDatabase.update("LOAISACH",contentValues,"maloai = ?",new String[]{String.valueOf(modelLoaisach.getMaloai())});
        return check!=0;
    }

    /*
     * -1: khong xoa duoc vi loi he thong
     * 0: khong xoa duoc vi rang buoc khoa ngoai
     * 1: xoa thanh cong
     */
    public int xoaloaisach(int maloai){
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();

        //kiem tra ton tai voi the loai dang thuc hien xoa

        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?",new String[]{String.valueOf(maloai)});
        if (cursor.getCount()>0){
            return 0; //khong xoa dc vi rang buoc khoa ngoai
        }else {
            int check=sqLiteDatabase.delete("LOAISACH","maloai=?",new String[]{String.valueOf(maloai)});
            if (check==0){
                return -1; //xoa khong duoc vi loi or khong tim thay loai sach can xoa
            }else {
                return 1;
            }
        }
    }
}
