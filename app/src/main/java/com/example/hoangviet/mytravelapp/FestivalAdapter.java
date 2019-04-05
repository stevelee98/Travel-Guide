package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.MyViewHolder> {
    private Context context;
    private List<FestivalItem> festivalItemList;

    public FestivalAdapter(Context context, List<FestivalItem> itemList){
        this.context = context;
        this.festivalItemList = itemList;
    }
    @NonNull
    @Override
    public FestivalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.festival_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        FestivalItem item = festivalItemList.get(i);
        myViewHolder.Name.setText(item.getName());

        Glide.with(context).load(item.getImgView()).into(myViewHolder.imageView);

        /* bắt sự kiện click của card view tại đây*/
    }

    @Override
    public int getItemCount() {
        return festivalItemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public ImageView imageView;

        public MyViewHolder(View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.fes_name);
            imageView = (ImageView) view.findViewById(R.id.fes_thumbnail);
        }
    }
}
