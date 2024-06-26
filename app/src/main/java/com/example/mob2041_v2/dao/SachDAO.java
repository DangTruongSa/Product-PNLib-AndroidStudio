package com.example.mob2041_v2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_v2.database.Dbhelper;
import com.example.mob2041_v2.model.ModelLoaisach;
import com.example.mob2041_v2.model.ModelSach;

import java.util.ArrayList;

public class SachDAO {
    private Dbhelper dbhelper;
    public SachDAO(Context c){
        dbhelper=new Dbhelper(c);
    }

    //lay danh list sach
    public ArrayList<ModelSach> getlistsach(){
        ArrayList<ModelSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT s.masach, s.tensach, s.tacgia, s.giaban, s.maloai, l.tenloai FROM SACH s, LOAISACH l WHERE s.maloai=l.maloai",null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                list.add(new ModelSach(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themsach(String tensach, String tacgia, int giaban, int maloai){
        SQLiteDatabase sqLiteDatabase =dbhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach",tensach);
        contentValues.put("tacgia",tacgia);
        contentValues.put("giaban",giaban);
        contentValues.put("maloai",maloai);

        long check=sqLiteDatabase.insert("SACH",null,contentValues);
        return check!=-1;

    }

    public boolean suasach(ModelSach modelSach){
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach",modelSach.getTensach());
        contentValues.put("tacgia",modelSach.getTacgia());
        contentValues.put("giaban",modelSach.getGiaban());
        contentValues.put("maloai",modelSach.getMaloai());

        int check=sqLiteDatabase.update("SACH",contentValues,"masach = ?",new String[]{String.valueOf(modelSach.getMasach())});
        return check!=0;
    }


    public int xoasach(int masach){
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();

        //kiem tra ton tai voi the loai dang thuc hien xoa

        int cursor=sqLiteDatabase.delete("SACH","masach=?",new String[]{String.valueOf(masach)});
            if (cursor==0){
                return -1; //xoa khong duoc vi loi or khong tim thay sach can xoa
            }else {
                return 1;
            }
    }

}
