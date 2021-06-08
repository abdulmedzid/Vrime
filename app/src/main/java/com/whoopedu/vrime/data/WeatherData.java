package com.whoopedu.vrime.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherData {

    private static final String API_KEY = "cade7ce05030f131adea4c05a946dd9c";


    public static void getWeatherData(Context ctx, Location location, TextView responseTextView) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ctx);

        String url = getUrl(location);

        Log.v("URL", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Display the first 500 characters of the response string.
                    responseTextView.setText(response);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseTextView.setText(Integer.toString(error.networkResponse.statusCode));
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private static String getUrl(Location location) {

        String baseUrl = "https://api.openweathermap.org/data/2.5/onecall";

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("lat", Float.toString(location.getLat()))
                .appendQueryParameter("lon", Float.toString(location.getLon()))
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
}