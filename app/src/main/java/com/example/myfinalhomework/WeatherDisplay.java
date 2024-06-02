package com.example.myfinalhomework;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class WeatherDisplay extends ListActivity {
    private static final String TAG = "WeatherDisplay";
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("myweather", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);
        Handler handler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg){
                if (msg.what == 5) {
                    Bundle bundle= (Bundle) msg.obj;
                    ArrayList<String> list2=bundle.getStringArrayList("mylist");
                    ListAdapter adapter=new ArrayAdapter<String>(WeatherDisplay.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        Log.i(TAG, "onCreate: start Thread");
        WeatherWeb html=new WeatherWeb();
        html.setHandler(handler);
        Thread t=new Thread(html);
        t.start();
    }
}