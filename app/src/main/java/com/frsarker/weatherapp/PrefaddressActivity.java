package com.frsarker.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import   androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PrefaddressActivity extends AppCompatActivity {
    ArrayList<Weather> list;
    String CITY;
    String API ="74e8a02c411d434cc84958080a15d38c";
    TextView titleText;
    TextView tempText;

    RecyclerView recyclerView;
    WeatherAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefaddress);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        CITY = pref.getString("pref", "");

        titleText = findViewById(R.id.pref_title);
        tempText  = findViewById(R.id.pref_temperature);
        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new WeatherAdapter();

        showWeatherinfo();

        recyclerView.setAdapter(adapter);
    }

    private void showWeatherinfo()  {

    }
}
