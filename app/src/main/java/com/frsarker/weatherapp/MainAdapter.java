package com.frsarker.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MainWeatherData> items;

    public MainAdapter(ArrayList<MainWeatherData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_list, parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainWeatherData item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MainWeatherData item){
        items.add(item);
    }

    public void setItems(ArrayList<MainWeatherData> items){
        this.items = items;
    }

    public MainWeatherData getWeather(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView temp;
        TextView time;
        ImageView imageProfile;

        public ViewHolder(View itemview) {
            super(itemview);
            time = itemview.findViewById(R.id.recy_dtTxt);
            temp = itemview.findViewById(R.id.recy_temp);
            imageProfile = itemview.findViewById(R.id.profile);
        }

        public void setItem(MainWeatherData item) {
            temp.setText(item.getTemp());
            time.setText(item.getTime());
            Glide.with(this.itemView).load(item.getProfile()).into(imageProfile);
        }
    }
}
