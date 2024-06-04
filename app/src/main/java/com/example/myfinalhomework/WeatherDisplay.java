package com.example.myfinalhomework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

public class WeatherDisplay extends Activity implements Runnable{
    private static final String TAG = "WeatherDisplay";
    Handler handler;
    private String logTime = "";
    private final String Time_SP_KEY = "lastTimeStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_display);
        SharedPreferences sp = getSharedPreferences("myweather", Context.MODE_PRIVATE);
        logTime = sp.getString(Time_SP_KEY, "");
        Log.i("List","lastTimeStr=" + logTime);

        Intent intent = getIntent();
        String city=intent.getStringExtra("city");
        Log.i(TAG, "city:"+city);

        TextView cityname =findViewById(R.id.textView1);
        TextView weather =findViewById(R.id.textView2);
        TextView tem =findViewById(R.id.textView3);
        ImageView imageView=findViewById(R.id.imageView);

        WeaDBManager weadbManager = new WeaDBManager(WeatherDisplay.this);
        WeatherItem weaItem=weadbManager.findByCityName(city);

        cityname.setText(city);
        weather.setText(weaItem.getWeather());
        tem.setText(weaItem.getTem());
        Glide.with(this).load(R.drawable.zhongyu).into(imageView);
        handler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg){
                if (msg.what == 5) {
                    Toast.makeText(WeatherDisplay.this, "天气数据更新完成！", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "handleMessage: 天气数据更新完成！");
                }
                super.handleMessage(msg);
            }
        };
        Log.i(TAG, "onCreate: start Thread");
        Thread t=new Thread(this);
        t.start();
    }
    public void run() {
        Log.i("List", "run:run()...");
        String curTimeStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).substring(0,13);
        Log.i("run", "curDateStr:" + curTimeStr + ",logDate:" + logTime);
        if (curTimeStr.equals(logTime)) {
            Log.i("run", "日期相等，从数据库中获取数据");
        } else {
            Log.i("run", "日期不相等，从网格中获取在线数据");
            try {
                List<WeatherItem> weaList = new ArrayList<WeatherItem>();
                Document doc = Jsoup.connect("http://www.nmc.cn/publish/forecast.html").get();
                for (int i = 1; i <= 8; i++) {
                    Element citylist = doc.getElementsByClass("row city-list").get(i);
                    Elements cities = citylist.children();
                    for (Element row : cities) {
                        Log.i(TAG, "weather:" + row.text());
                        String str[] = row.text().trim().split("\\s");
                        String city = str[0];
                        String weather = str[1];
                        String lowtemp = str[2];
                        String hightemp = str[4];
                        String tem = lowtemp + "~" + hightemp;
                        WeatherItem weaItem = new WeatherItem(city, weather,tem);
                        weaList.add(weaItem);
                    }
                }
                WeaDBManager weadbManager = new WeaDBManager(WeatherDisplay.this);
                weadbManager.deleteAll();
                Log.i("db", "删除所有记录");
                weadbManager.addAll(weaList);
                Log.i("db", "添加新记录集");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SharedPreferences sp = getSharedPreferences("myweather", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(Time_SP_KEY, curTimeStr);
            edit.commit();
            Message msg = handler.obtainMessage(5);
            handler.sendMessage(msg);
        }
    }
}