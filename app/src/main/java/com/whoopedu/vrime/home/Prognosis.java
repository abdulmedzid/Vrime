package com.whoopedu.vrime.home;

import com.whoopedu.vrime.data.open_weather_map.WeatherCondition;

public class Prognosis {
    private String mTime;
    private WeatherCondition mWeatherCondition;
    private int mTemperature;

    public Prognosis(String time, WeatherCondition weatherCondition, int temperature) {
        mTime = time;
        mWeatherCondition = weatherCondition;
        mTemperature = temperature;
    }

    public String getTime() {
        return mTime;
    }

    public WeatherCondition getWeatherCondition() {
        return mWeatherCondition;
    }

    public int getTemperature() {
        return mTemperature;
    }
}
