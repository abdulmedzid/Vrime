package com.whoopedu.vrime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.whoopedu.vrime.util.BaseActivity;

public class LocationActivity extends BaseActivity {

    private Toolbar mLocationToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLocationToolbar = findViewById(R.id.toolbar_location);
        mLocationToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32);
        mLocationToolbar.setNavigationOnClickListener(view -> finish());


    }
}