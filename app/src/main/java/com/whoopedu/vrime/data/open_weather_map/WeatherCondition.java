package com.whoopedu.vrime.data.open_weather_map;

public class WeatherCondition {
    public int id;
    public String main;
    public String description;

    public WeatherCondition(int id,
                            String main,
                            String description) {
        this.id = id;
        this.main = main;
        this.description = description;
    }
}