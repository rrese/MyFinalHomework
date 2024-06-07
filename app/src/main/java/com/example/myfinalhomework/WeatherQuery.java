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

        SearchView searchView =findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("点击搜索想要查询的城市");
//        为SearchView组件设置事件的监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//              实际应用中应该在该方法内执行实际查询
                Intent config = new Intent(WeatherQuery.this, WeatherDisplay.class);
                config.putExtra("city",query);
                startActivity(config);
                return false;
            }
//            用户输入时激发该方法
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
//                如果newText不是长度为0的字符串
                if (TextUtils.isEmpty(newText)){
//                    清除ListView的过滤
                    listView.clearTextFilter();
                    listView.setVisibility(View.GONE);
                }else {
//                    使用用户输入的内容对ListView的列表项进行过滤
                    listView.setFilterText(newText);
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
