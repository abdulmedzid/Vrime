package com.whoopedu.vrime.home;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whoopedu.vrime.R;
import com.whoopedu.vrime.data.open_weather_map.CurrentWeather;
import com.whoopedu.vrime.data.open_weather_map.DailyTemperature;
import com.whoopedu.vrime.data.open_weather_map.DailyWeather;
import com.whoopedu.vrime.data.open_weather_map.WeatherDataManager;
import com.whoopedu.vrime.data.locations.Location;
import com.whoopedu.vrime.data.open_weather_map.WeatherMapData;
import com.whoopedu.vrime.location.LocationActivity;
import com.whoopedu.vrime.util.BaseActivity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends BaseActivity implements WeatherDataManager.WeatherDataListener {

    private Toolbar mHomeToolbar;
    private TextView mLocationNameTextView;
    private Location mLocation;

    private TextView mTemperatureTextView;
    private TextView mFeelsLikeTextView;

    private ImageView mWeatherStatusImageView;
    private TextView mWeatherStatusTextView;

    private RecyclerView mHourlyPrognosisRecyclerView;
    private RecyclerView mDailyPrognosisRecyclerView;


    private WeatherDataManager mWeatherDataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadActionBar();
        mLocationNameTextView = findViewById(R.id.tv_location);
        mWeatherDataManager = new WeatherDataManager(this);

        mTemperatureTextView = findViewById(R.id.tv_temperature);
        mFeelsLikeTextView = findViewById(R.id.tv_feels_like);
        mWeatherStatusImageView = findViewById(R.id.iv_weather_status);
        mWeatherStatusTextView = findViewById(R.id.tv_weather_status);

        mHourlyPrognosisRecyclerView = findViewById(R.id.rv_hourly_prognosis);
        mHourlyPrognosisRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        mDailyPrognosisRecyclerView = findViewById(R.id.rv_daily_prognosis);
        mDailyPrognosisRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );
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

    private void fadeIn(View v) {
        v.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1.f);
        alphaAnimation.setDuration(450);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onLoadData(WeatherMapData wmp) {
        int temp = Math.round(wmp.current.temp - 273.15f);
        int feelsLike = Math.round(wmp.current.feels_like - 273.15f);

        mTemperatureTextView.setText(temp + "°");
        mFeelsLikeTextView.setText("feels like " + feelsLike + "°");

        fadeIn(mTemperatureTextView);
        fadeIn(mFeelsLikeTextView);
        fadeIn(mWeatherStatusImageView);
        fadeIn(mWeatherStatusTextView);

        mWeatherStatusImageView.setImageResource(
                WeatherIndicators.getImageResBasedOnWeatherStatusId(wmp.current.weather.get(0).id)
        );

        mWeatherStatusTextView.setText(wmp.current.weather.get(0).description);


        ArrayList<Prognosis> hourlyPrognosis = new ArrayList<>();

        for (CurrentWeather weather : wmp.hourly.subList(0, 24)) {

            Date date = new Date(weather.dt * 1000);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            String time = hours + "h";

            hourlyPrognosis.add(new Prognosis(
                time, weather.weather.get(0), Math.round(weather.temp - 273.15f)
            ));
        }

        WeatherPrognosisAdapter hourlyPrognosisAdapter = new WeatherPrognosisAdapter(hourlyPrognosis);
        mHourlyPrognosisRecyclerView.setAdapter(hourlyPrognosisAdapter);


        ArrayList<Prognosis> dailyPrognosis = new ArrayList<>();

        for (DailyWeather weather : wmp.daily) {

            Date date = new Date(weather.dt * 1000);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String time = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];

            dailyPrognosis.add(new Prognosis(
                    time, weather.weather.get(0), Math.round(weather.temp.day - 273.15f)
            ));
        }

        WeatherPrognosisAdapter dailyPrognosisAdapter = new WeatherPrognosisAdapter(dailyPrognosis);
        mDailyPrognosisRecyclerView.setAdapter(dailyPrognosisAdapter);
    }
}