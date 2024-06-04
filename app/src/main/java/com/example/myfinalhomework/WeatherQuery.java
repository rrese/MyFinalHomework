package com.example.myfinalhomework;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class WeatherQuery extends Activity {
    //private SearchView searchView;
    private static final String TAG = "weather";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_query);

    }
    public void click(View v){
        Intent config = new Intent(this, WeatherDisplay.class);

        if (v.getId() == R.id.btn_bj) {
            config.putExtra("city", "北京");
            Log.i(TAG, "city:beijing");
        } else if (v.getId() == R.id.btn_nj) {
            config.putExtra("city", "南京");
            Log.i(TAG, "city:nanjing");
        } else if (v.getId() == R.id.btn_sz) {
            config.putExtra("city", "深圳");
            Log.i(TAG, "city:shenzhen");
        }else if(v.getId() == R.id.btn_hz) {
            config.putExtra("city", "杭州");
            Log.i(TAG, "city:hangzhou");
        }else if (v.getId() == R.id.btn_wh) {
            config.putExtra("city", "武汉");
            Log.i(TAG, "city:wuhan");
        }else if (v.getId() == R.id.btn_cs) {
            config.putExtra("city", "长沙市");
            Log.i(TAG, "city:shanghai");
        }else if (v.getId() == R.id.btn_cd) {
            config.putExtra("city", "成都");
            Log.i(TAG, "city:chengdu");
        }else if (v.getId() == R.id.btn_xa) {
            config.putExtra("city", "西安");
            Log.i(TAG, "city:xian");
        }else if (v.getId() == R.id.btn_heb) {
            config.putExtra("city", "哈尔滨");
            Log.i(TAG, "city:haerbin");
        }
        startActivity(config);
    }
    public void moreclick(View v){
        Intent config = new Intent(this, CityList.class);
        startActivity(config);
    }
}