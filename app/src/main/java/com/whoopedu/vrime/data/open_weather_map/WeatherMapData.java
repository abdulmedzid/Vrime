package com.whoopedu.vrime.data.open_weather_map;


import java.util.ArrayList;

public class WeatherMapData {
    public CurrentWeather current;
    public ArrayList<CurrentWeather> hourly;
    public ArrayList<DailyWeather> daily;

    public WeatherMapData(CurrentWeather current,
                          ArrayList<CurrentWeather> hourly,
                          ArrayList<DailyWeather> daily) {
        this.current = current;
        this.hourly = hourly;
        this.daily = daily;
    }
}
