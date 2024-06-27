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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        UserDBManager userDBManager = new UserDBManager(MyCityWeather.this);
        WeaDBManager weadbManager = new WeaDBManager(MyCityWeather.this);
        if (curTimeStr.equals(logTime)) {
            Log.i("run", "日期相等，从数据库中获取数据");
        } else {
            Log.i("run", "日期不相等，更新数据库数据");
            try{
            List<WeatherItem> weaList = new ArrayList<WeatherItem>();
            Document doc = Jsoup.connect("http://www.nmc.cn/publish/forecast.html").get();
            for (int i = 1; i <= 8; i++) {
                Element citylist = doc.getElementsByClass("row city-list").get(i);
                Elements cities = citylist.children();
                for (Element row : cities) {
                    String str[] = row.text().trim().split("\\s");
                    String city = str[0];
                    String weather = str[1];
                    String lowtemp = str[2];
                    String hightemp = str[4];
                    String tem = lowtemp + "~" + hightemp;
                    WeatherItem weaItem = new WeatherItem(city, weather, tem);
                    weaList.add(weaItem);
                }
            }
            weadbManager.deleteAll();
            weadbManager.addAll(weaList);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sp.edit().putString(Time_SP_KEY, curTimeStr);
            sp.edit().commit();
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
        Toast.makeText(MyCityWeather.this, "已清空城市管理列表,请刷新!", Toast.LENGTH_SHORT).show();
    }
}