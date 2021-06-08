package com.whoopedu.vrime.data.open_weather_map;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.whoopedu.vrime.R;
import com.whoopedu.vrime.data.locations.Location;
import com.whoopedu.vrime.data.open_weather_map.WeatherMapData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class WeatherDataManager {

    private static final String API_KEY = "cade7ce05030f131adea4c05a946dd9c";
    private static final long UPDATE_DELAY = 60 * 60 * 1000;

    private WeatherDataListener mCallback;

    public WeatherDataManager(WeatherDataListener callback)  {
        mCallback = callback;
    }

    public void getWeatherData(Context ctx, Location location) {
        apiCall(ctx, location);
    }

    private WeatherMapData loadDataFromStorage(Context ctx) {
        String json = null;
        try {
            json = getJsonFromFile(ctx);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(json, WeatherMapData.class);
    }

    private static boolean isLocationChanged() {
        return  false;
    }

    private void saveJsonToFile(Context ctx, String response) {
        String filename = "weather_json";
        try (FileOutputStream fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(response.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonFromFile(Context ctx) throws FileNotFoundException {
        FileInputStream fis = ctx.openFileInput("weather_json");
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    private boolean isTimeForNewApiCall(Context ctx) {

        Date dateOfLastUpdate = getTimeOfLastWeatherUpdate(ctx);
        Date now = new Date();

        return now.getTime() - dateOfLastUpdate.getTime() > UPDATE_DELAY;
    }

    private Date getTimeOfLastWeatherUpdate(Context ctx) {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(ctx);

        long time = pref.getLong(
                ctx.getString(R.string.TIME_OF_WEATHER_UPDATE),
                0
        );

        return new Date(time);
    }

    private void setTimeOfLastWeatherUpdate(Context ctx) {
        SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(ctx).edit();

        editor.putLong(
                ctx.getString(R.string.TIME_OF_WEATHER_UPDATE),
                new Date().getTime()
        );

        editor.apply();
    }

    private void apiCall(Context ctx, Location location) {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        String url = getUrl(location);
        Log.v("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    saveJsonToFile(ctx, response);
                    setTimeOfLastWeatherUpdate(ctx);

                    Gson gson = new Gson();
                    WeatherMapData wmp = gson.fromJson(response, WeatherMapData.class);

                    mCallback.onLoadData(wmp);
                }, error -> {

        });

        queue.add(stringRequest);
    }


    private String getUrl(Location location) {

        String baseUrl = "https://api.openweathermap.org/data/2.5/onecall";

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("lat", Float.toString(location.getLat()))
                .appendQueryParameter("lon", Float.toString(location.getLon()))
                .appendQueryParameter("exclude", "hourly")
                .appendQueryParameter("appid", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
            return url.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public interface WeatherDataListener {
        public void onLoadData(WeatherMapData wmp);
    }

    public void setWeatherDataListener(WeatherDataListener wdl) {
        mCallback = wdl;
    }
}