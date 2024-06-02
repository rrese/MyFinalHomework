package com.example.myfinalhomework;

public class WeatherItem {
    private  String city;
    private String weather;
    private String tem;
    public WeatherItem(){
        super();
        weather="";
        tem="";
    }
    public WeatherItem(String weather,String tem){
        super();
        this.weather=weather;
        this.tem=tem;
    }
    public String getCity(){
        return city;
    }
    public  void setCity(String city){
        this.city=city;
    }
    public String getWeather(){
        return  weather;
    }
    public void setWeather(String weather){
        this.weather=weather;
    }
    public String getTem(){
        return  tem;
    }
    public void setTem(String tem){
        this.tem=tem;
    }
}

