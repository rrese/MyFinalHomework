package com.example.myfinalhomework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class WeatherQuery extends Activity {
    private static final String TAG = "weather";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_query);

//      以下搜索框内容引用自：https://blog.csdn.net/weixin_46157140/article/details/108213511
        SearchView searchView =findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("点击搜索想要查询的城市");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent config = new Intent(WeatherQuery.this, WeatherDisplay.class);
                config.putExtra("city",query);
                startActivity(config);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ListView listView = findViewById(R.id.listView);
                listView.setTextFilterEnabled(true);
                List<String> citylist = new ArrayList<String>();
                WeaDBManager weadbManager = new WeaDBManager(WeatherQuery.this);
                for (WeatherItem weaItem : weadbManager.listAll()) {
                    citylist.add(weaItem.getCity());
                }
                List<String> recitylist = (List<String>) citylist;
                ListAdapter adapter = new ArrayAdapter<String>(WeatherQuery.this, android.R.layout.simple_list_item_1, recitylist);
                listView.setAdapter(adapter);
                if (TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                    listView.setVisibility(View.GONE);
                }else {
//                    去掉filter方法过滤时出现的黑框，https://blog.csdn.net/w_xue/article/details/13773651
                    ((ArrayAdapter<?>) adapter).getFilter().filter(newText);
                    listView.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent config = new Intent(WeatherQuery.this, WeatherDisplay.class);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            config.putExtra("city",text.getText());
                            startActivity(config);
                        }
                    });
                }
                return true;
            }
        });
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
    public void myclick(View v) {
        Intent config = new Intent(this, MyCityWeather.class);
        startActivity(config);
    }
}