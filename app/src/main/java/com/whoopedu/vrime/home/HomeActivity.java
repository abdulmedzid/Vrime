package com.whoopedu.vrime.home;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.whoopedu.vrime.R;
import com.whoopedu.vrime.data.open_weather_map.WeatherCondition;
import com.whoopedu.vrime.data.open_weather_map.WeatherDataManager;
import com.whoopedu.vrime.data.locations.Location;
import com.whoopedu.vrime.data.open_weather_map.WeatherMapData;
import com.whoopedu.vrime.location.LocationActivity;
import com.whoopedu.vrime.util.BaseActivity;

public class HomeActivity extends BaseActivity implements WeatherDataManager.WeatherDataListener {

    private Toolbar mHomeToolbar;
    private TextView mLocationNameTextView;
    private Location mLocation;

    private TextView mTemperatureTextView;
    private ImageView mWeatherStatusImageView;

    private WeatherDataManager mWeatherDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadActionBar();
        mLocationNameTextView = findViewById(R.id.tv_location);
        mWeatherDataManager = new WeatherDataManager(this);

        mTemperatureTextView = findViewById(R.id.tv_temperature);
        mWeatherStatusImageView = findViewById(R.id.iv_weather_status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocation = Location.getLocation(this);
        mLocationNameTextView.setText(mLocation.getCityName());
        mWeatherDataManager.getWeatherData(this, mLocation);
    }

    /**
     * Toolbar menu
     */

    private void loadActionBar() {
        mHomeToolbar = findViewById(R.id.toolbar_main);
        mHomeToolbar.setTitle("");
        setSupportActionBar(mHomeToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.search_button) {
            runLocationActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void runLocationActivity() {
        Intent locationActivityIntent = new Intent(this, LocationActivity.class);
        startActivity(locationActivityIntent);
    }

    public enum WeatherConditions {
        THUNDERSTORM,
        RAIN,
        SNOW,
        ATMOSPHERE,
        CLEAR,
        CLOUDS
    }

    @Override
    public void onLoadData(WeatherMapData wmp) {
        int temp = Math.round(wmp.current.temp - 273.15f);
        mTemperatureTextView.setText(Integer.toString(temp) + "°");

        WeatherConditions weatherCondition = WeatherConditions.CLEAR;

        if (wmp.current.weather.get(0).id >= 200)
            weatherCondition = WeatherConditions.THUNDERSTORM;
        if (wmp.current.weather.get(0).id >= 300)
            weatherCondition = WeatherConditions.RAIN;
         if (wmp.current.weather.get(0).id >= 600)
            weatherCondition = WeatherConditions.SNOW;
         if (wmp.current.weather.get(0).id >= 700)
            weatherCondition = WeatherConditions.CLEAR;
         if (wmp.current.weather.get(0).id > 800)
            weatherCondition = WeatherConditions.CLOUDS;


        switch (weatherCondition) {

            case THUNDERSTORM:
                mWeatherStatusImageView.setImageResource(R.drawable.weather_icon_thunderstorm);
                break;
            case RAIN:
                mWeatherStatusImageView.setImageResource(R.drawable.weather_icon_rain);
                break;
            case SNOW:
                mWeatherStatusImageView.setImageResource(R.drawable.weather_icon_snow);
                break;
            case CLEAR:
                mWeatherStatusImageView.setImageResource(R.drawable.weather_icon_sunny);
                break;
            case CLOUDS:
                mWeatherStatusImageView.setImageResource(R.drawable.weather_icon_cloudy);
                break;
        }
    }
}