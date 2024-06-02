package com.example.myfinalhomework;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;


public class WeatherQuery extends AppCompatActivity {
    private SearchView searchView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_query);

    }
    public void click(View v){
            Intent config = new Intent(this, WeatherDisplay.class);

            startActivityForResult(config, 3);
        if (v.getId() == R.id.btn_bj) {
            config.putExtra("city", "北京");
        } else if (v.getId() == R.id.btn_sh) {

        } else if (v.getId() == R.id.btn_sz) {

        }else if (v.getId() == R.id.btn_nj) {

        }else if (v.getId() == R.id.btn_wh) {

        }else if (v.getId() == R.id.btn_cd) {

        }else if (v.getId() == R.id.btn_xa) {

        }else if (v.getId() == R.id.btn_heb) {

        }else if(v.getId() == R.id.btn_wlmq) {

        }
    }
}