package com.frsarker.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

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

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;

    private ArrayList<NewData> arrayList;
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

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        NewData newData = new NewData(R.drawable.humidity, "sunset", "06:40 AM");


        ivMenu = findViewById(R.id.iv_menu);
        likedMenu = findViewById(R.id.iv_liked);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation);

        setSupportActionBar(toolbar);

        arrayList.add(newData);
        mainAdapter.notifyDataSetChanged();

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

        likedMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CITY == null){
                    Toast.makeText(MainActivity.this, "지역을 검색해주세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "선호지역에 추가 되었습니다.", Toast.LENGTH_LONG).show();
                    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("city", CITY);
                    editor.commit();
                }
              }
        });

        navigationView.bringToFront();
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();

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
                    Intent i = new Intent(getApplicationContext(), SettingNotificationActivity.class);
                    startActivity(i);
                }
                    return true;
            }
        });

        searchTxt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override //입력받은 문자열 처리
            public boolean onQueryTextSubmit(String query){
                CITY = query;
                new weatherTask().execute(); //thread 실행
                return true;
            }

            @Override //입력란의 문자열이 바뀔 때 처리
            public boolean onQueryTextChange(String newText) {
                //리스트뷰 구현
                return false;
            }
        });
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
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }
         //백그라운드 작업이 긑난 뒤 호출, 메인 스레드에서 실행
        @Override
        protected void onPostExecute(String result) {
                updateWeather(result);
    }
}

    public void updateWeather(String result) {
        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject main = jsonObj.getJSONObject("main");
            JSONObject sys = jsonObj.getJSONObject("sys");
            JSONObject wind = jsonObj.getJSONObject("wind");
            JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

            Long updatedAt = jsonObj.getLong("dt");
            String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
            String temp = main.getString("temp") + "°C";
            String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
            String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
            String pressure = main.getString("pressure");
            String humidity = main.getString("humidity");

            Long sunrise = sys.getLong("sunrise");
            Long sunset = sys.getLong("sunset");
            String windSpeed = wind.getString("speed");
            String weatherDescription = weather.getString("description");

            String address = jsonObj.getString("name") + ", " + sys.getString("country");

            /* Populating extracted data into our views */
            addressTxt.setText(address);
            updated_atTxt.setText(updatedAtText);
            statusTxt.setText(weatherDescription.toUpperCase());
            tempTxt.setText(temp);
            temp_minTxt.setText(tempMin);
            temp_maxTxt.setText(tempMax);
            sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
            sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
            windTxt.setText(windSpeed);
            pressureTxt.setText(pressure);
            humidityTxt.setText(humidity);

            /* Views populated, Hiding the loader, Showing the main design */
            findViewById(R.id.loader).setVisibility(View.GONE);
            findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            findViewById(R.id.loader).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.VISIBLE);
        }
    }
    }

