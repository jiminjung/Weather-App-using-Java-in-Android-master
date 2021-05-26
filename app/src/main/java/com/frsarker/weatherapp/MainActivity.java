package com.frsarker.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String CITY;
    String API ="74e8a02c411d434cc84958080a15d38c";
    SearchView searchTxt;
    NavigationView navigationView;

     ImageView ivMenu;
     ImageView likedMenu;
     DrawerLayout drawerLayout;
     Toolbar toolbar;

    TextView addressTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt, updated_atTxt;

    private ArrayList<MainWeatherData> arrayList;
    private  MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        CITY = "Seoul";
        arrayList = new ArrayList<>();


        ivMenu = findViewById(R.id.iv_menu);
        likedMenu = findViewById(R.id.iv_liked);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation);

        setSupportActionBar(toolbar);

        searchTxt = findViewById(R.id.search_address);
        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        temp_minTxt = findViewById(R.id.temp_min);
        temp_maxTxt = findViewById(R.id.temp_max);
        sunriseTxt = findViewById(R.id.sunrise);
        sunsetTxt = findViewById(R.id.sunset);
        windTxt = findViewById(R.id.wind);
        pressureTxt = findViewById(R.id.pressure);
        humidityTxt = findViewById(R.id.humidity);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if (id == R.id.item1) {
                    Intent intent = new Intent(getApplicationContext(), PrefaddressActivity.class);
                    startActivity(intent);
                }
                if (id == R.id.item2) {
                    Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(i);
                }
                return true;
            }
        });

        searchTxt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CITY = query;
                new weatherTask().execute(); //thread 실행
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        new weatherTask().execute();
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* Showing the ProgressBar, Making the main design GONE */ //갱신작업
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/forecast?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }
         //백그라운드 작업이 끝난 뒤 호출, 메인 스레드에서 실행
        @Override
        protected void onPostExecute(String result) {
            updateWeather(result);
    }
}
        public void updateWeather(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                String list = jsonObj.getString("list");
                JSONArray jsonArray = new JSONArray(list);
//                JSONObject sys = jsonObj.getJSONObject("sys");
//                JSONObject wind = jsonObj.getJSONObject("wind");
//                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
//                JSONArray weather = list.getJSONArray(0).get

                /* Populating extracted data into our views */
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    JSONObject main = obj.getJSONObject("main");
                    JSONObject wind = obj.getJSONObject("wind");
                    JSONObject weather = obj.getJSONArray("weather").getJSONObject(0);

                    Long updatedAt = obj.getLong("dt");
                    String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                    String temp = main.getString("temp") + "°C";
                    String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                    String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                    String pressure = main.getString("pressure");
                    String humidity = main.getString("humidity");

                    String windSpeed = wind.getString("speed");
                    String weatherDescription = weather.getString("description");
                    String dt_txt = obj.getString("dt_txt"); //시간 설정

                    arrayList.add(new MainWeatherData(temp, pressure));
                    drawRecyclerView();
                    /* Populating extracted data into our views */
                    if (i == 1) {
                        updated_atTxt.setText(updatedAtText);
                        statusTxt.setText(weatherDescription.toUpperCase());
                        tempTxt.setText(temp);
                        temp_minTxt.setText(tempMin);
                        temp_maxTxt.setText(tempMax);
                        windTxt.setText(windSpeed);
                        pressureTxt.setText(pressure);
                        humidityTxt.setText(humidity);

                    }
                    /* Views populated, Hiding the loader, Showing the main design */
                }
                JSONObject city = jsonObj.getJSONObject("city");
                String address = city.getString("name") + ", " + city.getString("country");
                Long sunrise = city.getLong("sunrise");
                Long sunset = city.getLong("sunset");
                addressTxt.setText(address);
                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));

                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }
            public void drawRecyclerView () {
                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mainAdapter = new MainAdapter(arrayList);
                recyclerView.setAdapter(mainAdapter);
//            mainAdapter.notifyDataSetChanged();
            }

        }

