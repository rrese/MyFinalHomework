package com.example.myfinalhomework;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherWeb extends AppCompatActivity implements Runnable{
    private static final String TAG = "WeatherWeb";
    Handler handler;
    @Override
    public void run() {
        Log.i(TAG, "run: run()");
        URL url=null;
        Bundle bundle=new Bundle();
        ArrayList<String> list=new ArrayList<String>();
        try{
            Document doc = Jsoup.connect("http://www.nmc.cn/publish/forecast.html").get();
            for(int i=1;i<=8;i++) {
                Element citylist = doc.getElementsByClass("row city-list").get(i);
                Elements cities = citylist.children();
                for (Element row : cities) {
                    Log.i(TAG, "weather:" + row.text());

                }
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        Message msg=handler.obtainMessage(5,bundle);
        handler.sendMessage(msg);
    }
    public void setHandler(Handler handler) {
        this.handler=handler;
    }
}

