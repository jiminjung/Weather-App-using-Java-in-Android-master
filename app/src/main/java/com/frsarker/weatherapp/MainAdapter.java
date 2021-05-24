package com.frsarker.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<NewData> arrayList;

    public MainAdapter(ArrayList<NewData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.CustomViewHolder holder, int position) {
        holder.profile.setImageResource(arrayList.get(position).getProfile());
        holder.name.setText(arrayList.get(position).getName());
        holder.time.setText(arrayList.get(position).getTime());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String curName = holder.name.getText().toString();
                //   Toast.makeText(v.getContext().curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView profile;
        protected TextView name;
        protected  TextView time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profile = (ImageView)itemView.findViewById(R.id.profile);
            this.name = (TextView)itemView.findViewById(R.id.name);
            this.time = (TextView)itemView.findViewById(R.id.time);

        }
    }
}