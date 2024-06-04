package com.example.myfinalhomework;

public class WeatherItem {
    private  int id;
    private  String city;
    private String weather;
    private String tem;
    public WeatherItem(){
        super();
        city="";
        weather="";
        tem="";
    }
    public WeatherItem(String city,String weather,String tem){
        super();
        this.city=city;
        this.weather=weather;
        this.tem=tem;
    }
    public int getId(){
        return  id;
    }
    public  void setId(int id){
        this.id=id;
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