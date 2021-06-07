package com.whoopedu.vrime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.whoopedu.vrime.util.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherDataTextView = findViewById(R.id.tv_weather_data);

        loadWeatherData();
    }

    private void loadWeatherData() {
        String location = "London";
        new FetchWeatherTask().execute(location);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            // if no zip code passed return null
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse =
                        NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                /*
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimple

                 */
                String[] simpleData = new String[1];
                simpleData[0] = jsonWeatherResponse;
                return simpleData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            super.onPostExecute(weatherData);
            if (weatherData != null) {
                for (String weatherString : weatherData) {
                    mWeatherDataTextView.append((weatherString) + "\n\n\n");
                }
            }
        }
    }
}