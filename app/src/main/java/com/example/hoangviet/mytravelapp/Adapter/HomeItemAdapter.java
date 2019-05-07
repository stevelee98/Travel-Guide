package com.example.hoangviet.mytravelapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hoangviet.mytravelapp.FestivalItem;
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.example.hoangviet.mytravelapp.R;

import java.util.List;


public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.MyViewHolder> {
    private Context context;
    private List<HomeItemList> homeItemLists;

    public HomeItemAdapter(Context context, List<HomeItemList> itemList){
        this.context = context;
        this.homeItemLists = itemList;
    }
    @NonNull
    @Override
    public HomeItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home,viewGroup,false);
        return new HomeItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        HomeItemList item = homeItemLists.get(i);
        myViewHolder.Name.setText(item.getItemName());

        Glide.with(context).load(item.getImgView()).into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return homeItemLists.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public ImageView imageView;

        public MyViewHolder(View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.home_item_name);
            imageView = (ImageView) view.findViewById(R.id.item_thumbnail);
        }
    }
}

