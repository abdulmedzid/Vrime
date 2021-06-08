package com.whoopedu.vrime.data.open_weather_map;

import java.util.ArrayList;

public class CurrentWeather {
    public long dt;
    public float temp;
    public float feels_like;
    public float clouds;
    public ArrayList<WeatherCondition> weather;

    public CurrentWeather(long dt, float temp, float feels_like, float clouds, ArrayList<WeatherCondition> weather) {
        this.dt = dt;
        this.temp = temp;
        this.feels_like = feels_like;
        this.clouds = clouds;
        this.weather = weather;
    }
}
