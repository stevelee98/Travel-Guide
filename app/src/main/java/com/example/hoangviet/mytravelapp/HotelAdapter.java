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

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {
    private Context context;
    private List<HotelItem> hotelItemList;

    public HotelAdapter(Context context, List<HotelItem> itemList){
        this.context = context;
        this.hotelItemList = itemList;
    }
    @NonNull
    @Override
    public HotelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        HotelItem item = hotelItemList.get(i);
        myViewHolder.Name.setText(item.getHotelName());
        myViewHolder.Count.setText(item.getCountHotel());
        myViewHolder.DistanceHotel.setText(item.getDistanceHotel());
        myViewHolder.startHotel.setRating(item.getStarHotel());

        Glide.with(context).load(item.getImgHotel()).into(myViewHolder.imageView);

        /* bắt sự kiện click của card view tại đây*/
    }

    @Override
    public int getItemCount() {
        return hotelItemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public TextView Count;
        public TextView DistanceHotel;
        public ImageView imageView;
        public RatingBar startHotel;

        public MyViewHolder(View view){
            super(view);
            Name = (TextView) view.findViewById(R.id.hotel_name);
            imageView = (ImageView) view.findViewById(R.id.hotel_thumbnail);
            Count = (TextView) view.findViewById(R.id.txt_count_hotel);
            DistanceHotel = (TextView) view.findViewById(R.id.txt_distance_hotel);
            startHotel = (RatingBar) view.findViewById(R.id.hotel_rating);
        }
    }
}
