package com.whoopedu.vrime.home;

import com.whoopedu.vrime.R;

public class WeatherIndicators {

    public enum WeatherConditions {
        THUNDERSTORM,
        RAIN,
        SNOW,
        ATMOSPHERE,
        CLEAR,
        CLOUDS
    }

    public static int getImageResBasedOnWeatherStatusId(int statusId) {
        WeatherConditions weatherCondition = WeatherConditions.CLEAR;

        if (statusId >= 200)
            weatherCondition = WeatherConditions.THUNDERSTORM;
        if (statusId >= 300)
            weatherCondition = WeatherConditions.RAIN;
        if (statusId >= 600)
            weatherCondition = WeatherConditions.SNOW;
        if (statusId >= 700)
            weatherCondition = WeatherConditions.CLEAR;
        if (statusId > 800)
            weatherCondition = WeatherConditions.CLOUDS;

        switch (weatherCondition) {
            case THUNDERSTORM:
                return R.drawable.weather_icon_thunderstorm;
            case RAIN:
                return R.drawable.weather_icon_rain;
            case SNOW:
                return R.drawable.weather_icon_snow;
            case CLEAR:
                return R.drawable.weather_icon_sunny;
            case CLOUDS:
                return R.drawable.weather_icon_cloudy;
        }
        return R.drawable.weather_icon_sunny;
    }

    public static int getImageResBasedOnWeatherStatusIdandHour(int statusId, int hour) {
        WeatherConditions weatherCondition = WeatherConditions.CLEAR;

        if (statusId >= 200)
            weatherCondition = WeatherConditions.THUNDERSTORM;
        if (statusId >= 300)
            weatherCondition = WeatherConditions.RAIN;
        if (statusId >= 600)
            weatherCondition = WeatherConditions.SNOW;
        if (statusId >= 700)
            weatherCondition = WeatherConditions.CLEAR;
        if (statusId > 800)
            weatherCondition = WeatherConditions.CLOUDS;

        switch (weatherCondition) {
            case THUNDERSTORM:
                return R.drawable.weather_icon_thunderstorm;
            case RAIN:
                return R.drawable.weather_icon_rain;
            case SNOW:
                return R.drawable.weather_icon_snow;
            case CLEAR:
                if (hour >= 22 || hour < 5) {
                    return R.drawable.weather_icon_moonlight;
                } else {
                    return R.drawable.weather_icon_sunny;
                }
            case CLOUDS:
                return R.drawable.weather_icon_cloudy;
        }
        return R.drawable.weather_icon_sunny;
    }
}
