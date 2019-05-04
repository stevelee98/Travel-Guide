package com.example.hoangviet.mytravelapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomItemAdapter extends RecyclerView.Adapter<CustomItemAdapter.MyViewHolder> {
    private Context context;
    private List<ItemList> itemList;

    private static OnItemClickListener mOnClickItem;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnClickItem) {
        this.mOnClickItem = mOnClickItem;
    }


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
        myViewHolder.numStar.setText(item.getItemNumStar());
        myViewHolder.disTance.setText(item.getItemDistance());
        myViewHolder.openNow.setText(item.getopenNow());
        myViewHolder.ratingBar.setRating(Float.parseFloat(item.getItemNumStar().toString()));

//        myViewHolder.parrentLayout.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView Name;
        public TextView numStar;
        public TextView disTance;
        public RatingBar ratingBar;
        public TextView openNow;
        //public LinearLayout parrentLayout;


        public MyViewHolder(final View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.txt_item_name);
            numStar = (TextView) view.findViewById(R.id.item_num_start);
            disTance = (TextView) view.findViewById(R.id.txt_distance_item);
            ratingBar = (RatingBar) view.findViewById(R.id.item_rating);
            openNow = (TextView) view.findViewById(R.id.txt_open_now);

            //parrentLayout = view.findViewById(R.id.parent_layout);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mOnClickItem != null){
                        mOnClickItem.onItemClick(view, getLayoutPosition());
                    }
                }
            });

        }

    }

}

