package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PlacePhotoAdapter extends RecyclerView.Adapter<PlacePhotoAdapter.MyViewHolder>  {
    private Context context;
    private List<PlacePhotoItem> placePhotoList;

    public PlacePhotoAdapter(Context context, List<PlacePhotoItem> placePhotoList){
        this.context = context;
        this.placePhotoList = placePhotoList;
    }

    @NonNull
    @Override
    public PlacePhotoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        PlacePhotoItem itemPhoto = placePhotoList.get(i);

        Glide.with(context).load(itemPhoto.getPhoto()).into(myViewHolder.photo);

    }

    @Override
    public int getItemCount() {
        return placePhotoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.item_photo);
        }
    }
}
