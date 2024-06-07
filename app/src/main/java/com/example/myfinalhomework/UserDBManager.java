package com.example.myfinalhomework;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
public class UserDBManager {

    private DBHelper userdbhelper;
    private String TBNAME;

    public UserDBManager(Context context) {
        userdbhelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME_USER;
    }

    public void add(UserCityItem item){
        SQLiteDatabase db = userdbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CITY", item.getCity());
        values.put("WEATHER", item.getWeather());
        values.put("TEMPERATURE", item.getTem());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<UserCityItem> list){
        SQLiteDatabase db = userdbhelper.getWritableDatabase();
        for (UserCityItem item : list) {
            ContentValues values = new ContentValues();
            values.put("CITY", item.getCity());
            values.put("WEATHER", item.getWeather());
            values.put("TEMPERATURE", item.getTem());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void delete(String city) {
        SQLiteDatabase db = userdbhelper.getWritableDatabase();
        db.delete(TBNAME, "CITY=?", new String[]{city});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = userdbhelper.getWritableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }

    public void updata(UserCityItem item) {
        SQLiteDatabase db = userdbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CITY", item.getCity());
        values.put("WEATHER", item.getWeather());
        values.put("TEMPERATURE", item.getTem());
        db.update(TBNAME, values, "CITY=?", new String[]{item.getCity()});
        db.close();
    }

    @SuppressLint("Range")
    public List<UserCityItem> listAll(){
        List<UserCityItem> usercityList = null;
        SQLiteDatabase db = userdbhelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            usercityList = new ArrayList<UserCityItem>();
            while(cursor.moveToNext()){
                UserCityItem item = new UserCityItem();
                item.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
                item.setWeather(cursor.getString(cursor.getColumnIndex("WEATHER")));
                item.setTem(cursor.getString(cursor.getColumnIndex("TEMPERATURE")));
                usercityList.add(item);
            }
            cursor.close();
        }
        db.close();
        return usercityList;

    }

    @SuppressLint("Range")
    public UserCityItem findByCityName(String city){
        SQLiteDatabase db = userdbhelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "CITY=?", new String[]{city}, null, null, null);
        UserCityItem usercityItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            usercityItem = new UserCityItem();
            usercityItem.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
            usercityItem.setWeather(cursor.getString(cursor.getColumnIndex("WEATHER")));
            usercityItem.setTem(cursor.getString(cursor.getColumnIndex("TEMPERATURE")));
            cursor.close();
        }
        db.close();
        return usercityItem;
    }
}