package com.whoopedu.vrime.data.open_weather_map;

import java.util.ArrayList;

public class DailyWeather {
    public long dt;
    public DailyTemperature temp;
    public DailyTemperature feels_like;
    public float clouds;
    public ArrayList<WeatherCondition> weather;

    public DailyWeather(long dt,
                        DailyTemperature temp,
                        DailyTemperature feels_like,
                        float clouds,
                        ArrayList<WeatherCondition> weather) {
        this.dt = dt;
        this.temp = temp;
        this.feels_like = feels_like;
        this.clouds = clouds;
        this.weather = weather;
    }
}
