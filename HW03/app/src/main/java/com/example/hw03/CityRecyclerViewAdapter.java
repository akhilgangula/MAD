package com.example.hw03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {
    List<ForeCastEntry> forecast;
    public CityRecyclerViewAdapter(List<ForeCastEntry> forecast) {
        this.forecast = forecast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_card, parent, false);
        return new CityRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.temp.setText(forecast.get(position).main.temp);
        holder.tempMax.setText(forecast.get(position).main.temp);
        holder.tempMin.setText(forecast.get(position).main.temp);
        holder.humidity.setText(forecast.get(position).main.temp);
        holder.desc.setText(forecast.get(position).main.temp);
        holder.dateTime.setText(forecast.get(position).dt_txt);
        Picasso.get().load(Util.PIC_BASE_URL + Util.WEATHER_ICON + forecast.get(position).weather.get(0).icon + ".png").into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateTime, temp, tempMin, tempMax, humidity, desc;
        public final ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTime = itemView.findViewById(R.id.forecase_time);
            temp = itemView.findViewById(R.id.forecast_temp);
            tempMin = itemView.findViewById(R.id.forecast_min_temp);
            tempMax = itemView.findViewById(R.id.forecast_max_temp);
            humidity = itemView.findViewById(R.id.forecast_humidity);
            desc = itemView.findViewById(R.id.forecast_desc);
            imgView = itemView.findViewById(R.id.forecast_img);
        }
    }
}
