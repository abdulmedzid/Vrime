package com.whoopedu.vrime.data.open_weather_map;

public class DailyTemperature {
    public float day;
    public float night;
    public float eve;
    public float morn;

    public DailyTemperature(float day, float night, float eve, float morn) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }
}