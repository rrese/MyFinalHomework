package com.example.myfinalhomework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    private Context context;
    public MyAdapter(Context context, int resource, ArrayList<AdapterItem> list) {
        super(context, resource, list);
    }
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.myadapter,parent,false);
        }

        AdapterItem item = (AdapterItem) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);
        TextView content = (TextView) itemView.findViewById(R.id.itemContent);
        Button delete=(Button) itemView.findViewById(R.id.itemDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterItem clickedData = (AdapterItem) getItem(position);
                UserDBManager userdbManager = new UserDBManager(MyAdapter.this.getContext());
                userdbManager.delete(clickedData.getData1());
                Toast.makeText(MyAdapter.this.getContext(), "已从我的城市中删除,请刷新!", Toast.LENGTH_SHORT).show();
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterItem clickedData = (AdapterItem) getItem(position);
                Intent config = new Intent(getContext(), WeatherDisplay.class);
                config.putExtra("city", clickedData.getData1());
                getContext().startActivity(config);
            }
        });

        title.setText(item.getData1());
        detail.setText(item.getData2());
        content.setText(item.getData3());
        delete.setText("删除");
        return itemView;
    }
}