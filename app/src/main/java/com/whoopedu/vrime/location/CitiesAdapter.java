package com.whoopedu.vrime.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whoopedu.vrime.R;
import com.whoopedu.vrime.data.Location;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private List<Location> mLocations;
    private CitiesClickListener mCallback;


    public CitiesAdapter(List<Location> locations) {
        mLocations = locations;
    }

    public void setCallback(CitiesClickListener mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row_iem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.ViewHolder holder, int position) {
        Location location = mLocations.get(position);
        holder.mCityTextView.setText(
                location.getCityName()
        );
        holder.mCountryTextView.setText(
                location.getCountryName()
        );
    }

    @Override
    public int getItemCount() {

        if (mLocations != null) {
            return mLocations.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Location> locations) {
        mLocations = locations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCityTextView;
        private final TextView mCountryTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            mCityTextView = view.findViewById(R.id.tv_city);
            mCountryTextView = view.findViewById(R.id.tv_country);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                mCallback.onCityClick(mLocations.get(position));
            });
        }

        public TextView getCityTextView() {
            return mCityTextView;
        }

        public TextView getCountryTextView() {
            return mCountryTextView;
        }
    }

    public interface CitiesClickListener {
        public void onCityClick(Location location);
    }
}
