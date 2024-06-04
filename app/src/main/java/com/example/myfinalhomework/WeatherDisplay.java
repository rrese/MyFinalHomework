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
    String curTimeStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).substring(0, 13);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_display);
        SharedPreferences sp = getSharedPreferences("myweather", Context.MODE_PRIVATE);
        logTime = sp.getString(Time_SP_KEY, "");
        Log.i("List", "lastTimeStr=" + logTime);

        Log.i("run", "curDateStr:" + curTimeStr + ",logDate:" + logTime);
        if (curTimeStr.equals(logTime)) {
            Log.i("run", "日期相等，从数据库中获取数据");
        } else {
            Log.i("run", "日期不相等，从网格中获取在线数据");
            Log.i(TAG, "onCreate: start Thread");
            Thread t = new Thread(this);
            t.start();
        }

        handler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    Log.i(TAG, "handleMessage: 天气数据更新完成！");
                }
                super.handleMessage(msg);
            }
        };

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        Log.i(TAG, "city:" + city);

        TextView cityname = findViewById(R.id.textView1);
        TextView weathercondition = findViewById(R.id.textView2);
        TextView tem = findViewById(R.id.textView3);
        ImageView imageView = findViewById(R.id.imageView);

        WeaDBManager weadbManager = new WeaDBManager(WeatherDisplay.this);
        WeatherItem weaItem = weadbManager.findByCityName(city);

        try {
            cityname.setText(city);
            weathercondition.setText(weaItem.getWeather());
            tem.setText(weaItem.getTem());
            String weather=weaItem.getWeather();
            if(weather.contains("转")){
                weather=weather.substring(weather.indexOf("转")+1);
                Log.i(TAG, "weatherstr: "+weather);
            }
             if(weather.equals("晴")){
                Glide.with(this).load(R.drawable.qingtian).into(imageView);
            }else if(weather.equals("阴")){
                Glide.with(this).load(R.drawable.yintian).into(imageView);
            }else if(weather.equals("多云")){
                 Glide.with(this).load(R.drawable.duoyun).into(imageView);
             }else if(weather.equals("小雨")){
                Glide.with(this).load(R.drawable.xiaoyu).into(imageView);
            }else if(weather.equals("中雨")){
                Glide.with(this).load(R.drawable.zhongyu).into(imageView);
            }else if(weather.equals("大雨")){
                Glide.with(this).load(R.drawable.dayu).into(imageView);
            }else if(weather.equals("暴雨")){
                Glide.with(this).load(R.drawable.baoyu).into(imageView);
            }else if(weather.equals("阵雨")){
                 Glide.with(this).load(R.drawable.zhenyu).into(imageView);
             }else if(weather.equals("雷阵雨")){
                 Glide.with(this).load(R.drawable.leizhenyu).into(imageView);
             }else if(weather.equals("冻雨")){
                 Glide.with(this).load(R.drawable.dongyu).into(imageView);
             }else if(weather.equals("小雪")){
                Glide.with(this).load(R.drawable.xiaoxue).into(imageView);
            }else if(weather.equals("中雪")){
                Glide.with(this).load(R.drawable.zhongxue).into(imageView);
            }else if(weather.equals("大雪")){
                Glide.with(this).load(R.drawable.daxue).into(imageView);
            }else if(weather.equals("暴雪")){
                Glide.with(this).load(R.drawable.baoxue).into(imageView);
            }else if(weather.equals("雨夹雪")){
                Glide.with(this).load(R.drawable.yujiaxue).into(imageView);
            }else if(weather.equals("雾")){
                 Glide.with(this).load(R.drawable.wu).into(imageView);
             }else if(weather.equals("雾霾")){
                Glide.with(this).load(R.drawable.wumai).into(imageView);
            }else if(weather.equals("沙尘暴")){
                Glide.with(this).load(R.drawable.shachenbao).into(imageView);
            }else if(weather.equals("扬沙")){
                Glide.with(this).load(R.drawable.yangsha).into(imageView);
            }
        } catch (Exception e) {
            Toast.makeText(this, "暂无数据!", Toast.LENGTH_SHORT).show();
        }
        }
        public void run () {
            try {
                Log.i("List", "run:run()...");
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
                        WeatherItem weaItem = new WeatherItem(city, weather, tem);
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