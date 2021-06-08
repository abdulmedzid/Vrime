package com.whoopedu.vrime.data.locations;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.whoopedu.vrime.R;

public class Location {
    private int mId;
    private String mCityName;
    private String mCountryName;
    private float mLat;
    private float mLon;

    public Location(
            int id,
            String cityName,
            String countryName,
            float lat,
            float lon) {
        mId = id;
        mCityName = cityName;
        mCountryName = countryName;
        mLat = lat;
        mLon = lon;
    }

    public int getId() {
        return mId;
    }

    public String getCityName() {
        return mCityName;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public float getLat() {
        return mLat;
    }

    public float getLon() {
        return mLon;
    }

    public static void setLocation(Context ctx, Location location) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(ctx);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        // id
        editor.putInt(
                ctx.getString(R.string.CURRENT_LOCATION_ID),
                location.getId()
        );

        // name
        editor.putString(
                ctx.getString(R.string.CURRENT_LOCATION),
                location.getCityName()
        );

        // country
        editor.putString(
                ctx.getString(R.string.CURRENT_COUNTRY),
                location.getCountryName()
        );

        // lat
        editor.putFloat(
                ctx.getString(R.string.CURRENT_LOCATION_LAT),
                location.getLat()
        );

        // lon
        editor.putFloat(
                ctx.getString(R.string.CURRENT_LOCATION_LON),
                location.getLon()
        );

        editor.apply();
    }

    public static Location getLocation(Context ctx) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(ctx);

        int id = sharedPreferences.getInt(
                ctx.getString(R.string.CURRENT_LOCATION_ID),
                3194828 // id of Mostar
        );

        String name = sharedPreferences.getString(
                ctx.getString(R.string.CURRENT_LOCATION),
                "Mostar"
        );

        String country = sharedPreferences.getString(
                ctx.getString(R.string.CURRENT_LOCATION),
                "BA"
        );

        float lat = sharedPreferences.getFloat(
                ctx.getString(R.string.CURRENT_LOCATION_LAT),
                0.0f
        );

        float lon = sharedPreferences.getFloat(
                ctx.getString(R.string.CURRENT_LOCATION_LON),
                0.0f
        );

        return new Location(
                id,
                name,
                country,
                lat,
                lon
        );
    }
}
