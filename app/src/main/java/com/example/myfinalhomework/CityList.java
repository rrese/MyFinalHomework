package com.example.myfinalhomework;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class CityList extends ListActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "city";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> citylist = new ArrayList<String>();
        WeaDBManager weadbManager = new WeaDBManager(CityList.this);

        for (WeatherItem weaItem : weadbManager.listAll()) {
            citylist.add(weaItem.getCity());
        }
        List<String> recitylist = (List<String>) citylist;
        ListAdapter adapter = new ArrayAdapter<String>(CityList.this, android.R.layout.simple_list_item_1, recitylist);
        setListAdapter(adapter);
        Log.i(TAG, "citylist...");

        getListView().setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent config = new Intent(this, WeatherDisplay.class);

        TextView text = (TextView) view.findViewById(android.R.id.text1);
        config.putExtra("city",text.getText());
        Log.i(TAG, "city:"+String.valueOf(text.getText()));

        startActivity(config);
    }
}