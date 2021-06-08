package com.whoopedu.vrime;

import android.content.Intent;
import android.os.Bundle;

import com.whoopedu.vrime.home.HomeActivity;
import com.whoopedu.vrime.util.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Intent homeActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(homeActivityIntent);

        finish();
    }
}