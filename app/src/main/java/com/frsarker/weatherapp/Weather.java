package com.frsarker.weatherapp;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
    String temp;
    String address;
    String profile;

    public Weather(String temp,String address,String profile){
        this.temp = temp;
        this.address = address;
        this.profile = profile;
    }

    public void setTemp(String temp){
        this.temp = temp;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setProfile(String weatherImage) {this.profile = weatherImage;}

    public String getTemp(){
        return temp;
    }

    public String getAddress(){
        return address;
    }

    public String getProfile(){
        return profile;
    }

}
