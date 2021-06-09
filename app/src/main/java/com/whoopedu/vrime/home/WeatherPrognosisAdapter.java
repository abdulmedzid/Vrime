package com.whoopedu.vrime.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whoopedu.vrime.R;
import com.whoopedu.vrime.location.CitiesAdapter;

import java.util.ArrayList;

public class WeatherPrognosisAdapter  extends RecyclerView.Adapter<WeatherPrognosisAdapter.ViewHolder> {

    private ArrayList<Prognosis> mPrognoses;

    public WeatherPrognosisAdapter(ArrayList<Prognosis> prognoses) {
        mPrognoses = prognoses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prognosis_segment_item, parent, false);
        return new WeatherPrognosisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prognosis prognosis = mPrognoses.get(position);
        holder.getTemperatureTextView().setText(
                Integer.toString(prognosis.getTemperature()) + "°"
        );

        String time = prognosis.getTime();
        int hours = 12;
        if (isHourTime(prognosis.getTime())) {
            hours = Integer.parseInt(time.substring(0, time.length() - 1));
            Log.d("hours", Integer.toString(hours));
        }

        holder.getTimeTextView().setText(time);

        holder.getWeatherStatusImageView().setImageResource(
                WeatherIndicators.getImageResBasedOnWeatherStatusIdandHour(
                        prognosis.getWeatherCondition().id, hours
                )
        );
    }

    private boolean isHourTime(String time) {
        return time.endsWith("h");
    }

    @Override
    public int getItemCount() {
        return mPrognoses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimeTextView;
        private TextView mTemperatureTextView;
        private ImageView mWeatherStatusImageView;

        public ViewHolder(@NonNull View view) {
            super(view);

            mTimeTextView = view.findViewById(R.id.tv_time);
            mTemperatureTextView = view.findViewById(R.id.tv_temperature);
            mWeatherStatusImageView = view.findViewById(R.id.iv_weather_status);
        }

        public ImageView getWeatherStatusImageView() {
            return mWeatherStatusImageView;
        }

        public TextView getTemperatureTextView() {
            return mTemperatureTextView;
        }

        public TextView getTimeTextView() {
            return mTimeTextView;
        }
    }
}
