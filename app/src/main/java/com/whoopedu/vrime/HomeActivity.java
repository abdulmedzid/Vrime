package com.whoopedu.vrime;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.whoopedu.vrime.data.WeatherData;
import com.whoopedu.vrime.data.Location;
import com.whoopedu.vrime.location.LocationActivity;
import com.whoopedu.vrime.util.BaseActivity;

public class HomeActivity extends BaseActivity {

    private Toolbar mHomeToolbar;

    private TextView mLocationNameTextView;

    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadActionBar();
        mLocationNameTextView = findViewById(R.id.tv_location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocation = Location.getLocation(this);
        mLocationNameTextView.setText(mLocation.getCityName());

        WeatherData.getWeatherData(this, mLocation, findViewById(R.id.tv_result));
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
}