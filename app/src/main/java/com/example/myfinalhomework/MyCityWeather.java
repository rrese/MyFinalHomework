package com.example.myfinalhomework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyCityWeather extends Activity {
    private static final String TAG = "mycity";
    private String logTime = "";
    private final String Time_SP_KEY = "lastTimeStr";
    String curTimeStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).substring(0, 13);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_mycity);

        SharedPreferences sp = getSharedPreferences("myweather", Context.MODE_PRIVATE);
        logTime = sp.getString(Time_SP_KEY, "");
        Log.i("List", "lastTimeStr=" + logTime);
        Log.i("run", "curDateStr:" + curTimeStr + ",logDate:" + logTime);

        UserDBManager userDBManager = new UserDBManager(MyCityWeather.this);
        WeaDBManager weadbManager = new WeaDBManager(MyCityWeather.this);
        if (curTimeStr.equals(logTime)) {
            Log.i("run", "日期相等，从数据库中获取数据");
        } else {
            Log.i("run", "日期不相等，更新数据库数据");
            for (UserCityItem userItem : userDBManager.listAll()) {
                WeatherItem weaItem = weadbManager.findByCityName(userItem.getCity());
                userDBManager.updata(new UserCityItem(weaItem.getCity(), weaItem.getWeather(), weaItem.getTem()));
            }
        }
        ArrayList<AdapterItem> listItems = new ArrayList<>();
        for (UserCityItem userItem : userDBManager.listAll()) {
            listItems.add(new AdapterItem(userItem.getCity(), userItem.getWeather(), userItem.getTem()));
        }

        MyAdapter myAdapter = new MyAdapter(this, R.layout.myadapter, listItems);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
        Log.i(TAG, "mycitylist");
    }

    public void backclick(View v) {
        Intent config = new Intent(this, WeatherQuery.class);
        startActivity(config);
    }

    public void deleteallclick(View v) {
        UserDBManager userDBManager = new UserDBManager(MyCityWeather.this);
        userDBManager.deleteAll();
        Log.i("userdb", "删除所有记录");
        Toast.makeText(MyCityWeather.this, "已从我的城市中删除,请刷新!", Toast.LENGTH_SHORT).show();
    }
}