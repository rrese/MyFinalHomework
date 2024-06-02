package com.example.myfinalhomework;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(WeatherItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("city", item.getCity());
        values.put("weather", item.getWeather());
        values.put("tem", item.getTem());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<WeatherItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (WeatherItem item : list) {
            ContentValues values = new ContentValues();
            values.put("city", item.getCity());
            values.put("weather", item.getWeather());
            values.put("tem", item.getTem());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void delete(String city) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "CITY=?", new String[]{city});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }

    public void updata(WeatherItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("city", item.getCity());
        values.put("weather", item.getWeather());
        values.put("tem", item.getTem());
        db.update(TBNAME, values, "CITY=?", new String[]{item.getCity()});
        db.close();
    }

    @SuppressLint("Range")
    public List<WeatherItem> listAll(){
        List<WeatherItem> weaList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            weaList = new ArrayList<WeatherItem>();
            while(cursor.moveToNext()){
                WeatherItem item = new WeatherItem();
                item.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
                item.setWeather(cursor.getString(cursor.getColumnIndex("WEATHER")));
                item.setTem(cursor.getString(cursor.getColumnIndex("TEMPERATURE")));
                weaList.add(item);
            }
            cursor.close();
        }
        db.close();
        return weaList;

    }

    @SuppressLint("Range")
    public WeatherItem findByCityName(String city){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "CITY=?", new String[]{city}, null, null, null);
        WeatherItem weaItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            weaItem = new WeatherItem();
            weaItem.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
            weaItem.setWeather(cursor.getString(cursor.getColumnIndex("WEATHER")));
            weaItem.setTem(cursor.getString(cursor.getColumnIndex("TEMPERATURE")));
            cursor.close();
        }
        db.close();
        return weaItem;
    }
}
