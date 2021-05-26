package com.frsarker.weatherapp;

public class MainWeatherData {
    private  int profile;
    private  String name;
    private  String time;

    public MainWeatherData(int profile, String name, String time) {
        this.profile = profile;
        this.name = name;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}