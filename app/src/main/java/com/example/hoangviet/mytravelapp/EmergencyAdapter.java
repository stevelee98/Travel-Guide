package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.MyViewHolder> {
    private Context context;
    private List<EmergencyItem> emItemList;

    public EmergencyAdapter(Context context, List<EmergencyItem> itemList){
        this.context = context;
        this.emItemList = itemList;
    }
    @NonNull
    @Override
    public EmergencyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emergency_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        EmergencyItem item = emItemList.get(i);
        myViewHolder.Name.setText(item.getEmName());
        myViewHolder.countRating.setText(item.getEmRatingCount());
        myViewHolder.disEmergency.setText(item.getEmDistance());
        myViewHolder.ratingEm.setRating(Float.parseFloat(item.getEmRating()));
        /* bắt sự kiện click của card view tại đây*/
    }

    @Override
    public int getItemCount() {
        return emItemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public TextView countRating;
        public TextView disEmergency;
        public RatingBar ratingEm;

        public MyViewHolder(View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.txt_emergency_name);
            countRating = (TextView) view.findViewById(R.id.em_num_start);
            disEmergency = (TextView) view.findViewById(R.id.txt_distance_em);
            ratingEm = (RatingBar) view.findViewById(R.id.em_rating);
        }
    }
}

