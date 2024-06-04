package com.example.myfinalhomework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class WelcomeActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView imageView=findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.welcome).into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this, WeatherQuery.class);
                startActivity(mainIntent);
                finish();
            }
        },2000);
    }
}
