package com.frsarker.weatherapp;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    ArrayList<Weather> items = new ArrayList<Weather>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.weather_item, parent,false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Weather item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Weather item){
        items.add(item);
    }

    public void setItems(ArrayList<Weather> items){
        this.items = items;
    }
    public Weather getWeather(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView weatherImage;
        TextView temp;
        TextView address;

        public ViewHolder(View itemview) {
            super(itemview);

            temp = itemview.findViewById(R.id.pref_temperature);
            address = itemview.findViewById(R.id.pref_title);
        }

        public void setItem(Weather item) {
            temp.setText(item.temp);
            address.setText(item.address);
        }
    }
}
