package com.frsarker.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.println;

public class PrefaddressActivity extends AppCompatActivity {
    ArrayList<Weather> list;
    String API = "74e8a02c411d434cc84958080a15d38c";

    ImageView likedMenu;
    RecyclerView recyclerView;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefaddress);
        list = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        likedMenu = findViewById(R.id.iv_liked);
        likedMenu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(PrefaddressActivity.this, R.style.MyAlertDialogStyle);
                alert.setMessage("선호지역을 입력하세요");
                alert.setCancelable(false);
                alert.setView(R.layout.addbox_dialog);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    //확인버튼 눌렀을 경우
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog) dialog;
                        EditText input = (EditText) f.findViewById(R.id.dialog);
                        makeRequest(input.getText().toString());
                        Toast.makeText(getApplicationContext(), "선호지역에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
//        drawRecyclerView();
    }

    private void drawRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        WeatherAdapter adapter = new WeatherAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public void makeRequest(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=" + API;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                println("응답->" + response);
                processResponse(response);
            }
        },
         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("에러->" + error.getMessage());
                Toast.makeText(PrefaddressActivity.this, "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    }
          }
        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        println("요청 보냄");
    }

    public void println(String data) {
        Log.d("PrefaddressActivity", data);
    }

    public void processResponse(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject main = jsonObj.getJSONObject("main");
            String temp = main.getString("temp") + "°C";
            String feelslike = main.getString("feels_like") + "°C";
            String address = jsonObj.getString("name");

            list.add(new Weather(temp, feelslike, address));
            drawRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




