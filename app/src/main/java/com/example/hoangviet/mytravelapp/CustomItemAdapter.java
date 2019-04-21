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

public class CustomItemAdapter extends RecyclerView.Adapter<CustomItemAdapter.MyViewHolder> {
    private Context context;
    private List<ItemList> itemList;

    public CustomItemAdapter(Context context, List<ItemList> itemList){
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public CustomItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ItemList item = itemList.get(i);
        myViewHolder.Name.setText(item.getItemName());
        myViewHolder.countRating.setText(item.getItemRatingCount());
        myViewHolder.disTance.setText(item.getItemDistance());
        myViewHolder.rating.setRating(Float.parseFloat(item.getItemRating()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public TextView countRating;
        public TextView disTance;
        public RatingBar rating;

        public MyViewHolder(View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.txt_item_name);
            countRating = (TextView) view.findViewById(R.id.item_num_start);
            disTance = (TextView) view.findViewById(R.id.txt_distance_item);
            rating = (RatingBar) view.findViewById(R.id.item_rating);
        }
    }
}

