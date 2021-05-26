package com.frsarker.weatherapp;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
    String temp;

    String address;

    public Weather(String temp,String address){
        this.temp = temp;
        this.address = address;
    }

    public void setTemp(String temp){
        this.temp = temp;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getTemp(){
        return temp;
    }

    public String getAddress(){
        return address;
    }

}
