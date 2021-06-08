package com.whoopedu.vrime.location;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.whoopedu.vrime.R;
import com.whoopedu.vrime.data.locations.CityReaderContract;
import com.whoopedu.vrime.data.locations.Location;
import com.whoopedu.vrime.data.locations.LocationsDatabase;
import com.whoopedu.vrime.util.BaseActivity;
import com.whoopedu.vrime.util.ImpatientClickListener;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BaseActivity implements CitiesAdapter.CitiesClickListener {

    private Toolbar mLocationToolbar;
    private EditText mLocationEditText;
    private FrameLayout mDefocuser;

    private RecyclerView mLocationsRecycler;
    private CitiesAdapter mLocationsAdapter = new CitiesAdapter(null);

    private LocationsDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLocationToolbar = findViewById(R.id.toolbar_location);
        mLocationToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32);
        mLocationToolbar.setNavigationOnClickListener(view -> finish());

        mLocationEditText = findViewById(R.id.et_location);
        mLocationsRecycler = findViewById(R.id.rv_location_search_results);
        mLocationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mLocationsRecycler.setAdapter(mLocationsAdapter);
        mLocationsAdapter.setCallback(this);

        mDefocuser = findViewById(R.id.defocuser);
        mDefocuser.setOnTouchListener(new ImpatientClickListener() {
            @Override
            public void onClick() {
                hideSoftKeyboard();
            }
        });

        mLocationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Location> locations = new ArrayList<>();
                if (s != null && s.length() != 0) {
                    SQLiteDatabase readableDatabase = mDatabase.getReadableDatabase();

                    Log.v("query", "SELECT name FROM Cities WHERE name LIKE '" + s + "%'");

                    Cursor cursor = readableDatabase.rawQuery(
                            "SELECT * FROM Cities WHERE name LIKE '" + s + "%' LIMIT 15",
                            null
                    );

                    while (cursor.moveToNext()) {
                        locations.add(getLocationFromCursor(cursor));
                    }

                    mLocationsAdapter.setData(locations);
                    mLocationsAdapter.notifyDataSetChanged();

                    cursor.close();
                }
                mLocationsAdapter.setData(locations);
                mLocationsAdapter.notifyDataSetChanged();
            }
        });

        mDatabase = new LocationsDatabase(this);
    }

    private Location getLocationFromCursor(Cursor cursor) {
        int id = cursor.getInt(
                cursor.getColumnIndexOrThrow(CityReaderContract.CityEntry.COLUMN_NAME_ID)
        );
        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(CityReaderContract.CityEntry.COLUMN_NAME_NAME)
        );
        String country = cursor.getString(
                cursor.getColumnIndexOrThrow(CityReaderContract.CityEntry.COLUMN_NAME_COUNTRY)
        );
        float lon = cursor.getFloat(4);
        float lat = cursor.getFloat(5);
        return new Location(id, name, country, lat, lon);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @Override
    public void onCityClick(Location location) {
        Location.setLocation(this, location);
        finish();
    }
}