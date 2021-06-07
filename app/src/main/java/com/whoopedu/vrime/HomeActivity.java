package com.whoopedu.vrime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.whoopedu.vrime.util.BaseActivity;
import com.whoopedu.vrime.util.NetworkUtils;

import java.net.URL;

public class HomeActivity extends BaseActivity {

    private Toolbar mHomeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadActionBar();
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