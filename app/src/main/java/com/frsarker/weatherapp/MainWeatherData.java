package com.frsarker.weatherapp;

public class MainWeatherData {
//    private  int profile;
    private  String temp;
    private  String pressure;

    public MainWeatherData(String temp, String pressure) {
        this.temp = temp;
        this.pressure = pressure;
    }

    public void setTemp(String temp){
        this.temp= temp;
    }
    public String getTemp(){
        return temp;
    }

    public void setPressure(String pressure){
        this.pressure = pressure;
    }

    public String getPressure(){
        return pressure;
    }


}