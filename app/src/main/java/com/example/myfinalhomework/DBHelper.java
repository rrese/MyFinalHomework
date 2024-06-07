package com.example.myfinalhomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "weather.db";
    public static final String TB_NAME_WEA = "tb_weather";
    public static final String TB_NAME_USER = "tb_usercity";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME_WEA+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CITY TEXT,WEATHER TEXT,TEMPERATURE TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME_USER+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CITY TEXT,WEATHER TEXT,TEMPERATURE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
