package com.frsarker.weatherapp;

public class MainWeatherData {
//    private  int profile;
    private String temp;
    private String time;
    private String profile;

    public MainWeatherData(String temp, String time, String profile) {
        this.temp = temp;
        this.time = time;
        this.profile = profile;
    }

    public void setTemp(String temp){
        this.temp= temp;
    }
    public String getTemp(){
        return temp;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setProfile(String profile){
        this.profile = profile;
    }

    public String getProfile(){
        return profile;
    }


}