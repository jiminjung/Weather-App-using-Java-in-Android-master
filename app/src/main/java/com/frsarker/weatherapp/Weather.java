package com.frsarker.weatherapp;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
    String temp;
    String feelstemp;
    String address;

    public Weather(String temp, String feelstemp, String address){
        this.temp = temp;
        this.feelstemp = feelstemp;
        this.address = address;
    }

    public void setTemp(String temp){
        this.temp = temp;
    }

    public void setFeelstemp(String feelstemp){
        this.feelstemp = feelstemp;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getTemp(){
        return temp;
    }

    public String getFeelstemp(){
        return feelstemp;
    }

    public String getAddress(){
        return address;
    }

}
