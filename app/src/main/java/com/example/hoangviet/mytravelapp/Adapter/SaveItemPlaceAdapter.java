package com.example.hoangviet.mytravelapp.Adapter;

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
import com.example.hoangviet.mytravelapp.ItemsList.HomeItemList;
import com.example.hoangviet.mytravelapp.R;

import java.util.List;

public class SaveItemPlaceAdapter extends RecyclerView.Adapter<SaveItemPlaceAdapter.MyViewHolder> {
    private Context context;
    private List<HomeItemList> homeItemLists;

    private static SavePlaceOnItemClickListener mOnClickItem;

    public interface SavePlaceOnItemClickListener {
        void onItemClick(View itemView, int position, List<HomeItemList> lists);
    }

    public void setOnItemClickListener(SavePlaceOnItemClickListener mOnClickItem) {
        this.mOnClickItem = mOnClickItem;
    }

    public SaveItemPlaceAdapter(Context context, List<HomeItemList> itemList){
        this.context = context;
        this.homeItemLists = itemList;
    }
    @NonNull
    @Override
    public SaveItemPlaceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_save_place,viewGroup,false);
        return new SaveItemPlaceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        HomeItemList item = homeItemLists.get(i);
        myViewHolder.Name.setText(item.getItemName());

        Glide.with(context).load(item.getImgView()).into(myViewHolder.imageView);
        myViewHolder.Name.setText(item.getItemName());
        myViewHolder.numRating.setText(item.getItemNumStar());
        myViewHolder.openNow.setText(item.getOpenNow());
        myViewHolder.ratingBar.setRating(Float.parseFloat(item.getItemNumStar().toString()));

    }

    @Override
    public int getItemCount() {
        return homeItemLists.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public ImageView imageView;
        public RatingBar ratingBar;
        public TextView numRating;
        public TextView openNow;

        public MyViewHolder(View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.save_place_name);
            imageView = (ImageView) view.findViewById(R.id.save_place_img);
            ratingBar = (RatingBar) view.findViewById(R.id.save_place_ratingbar);
            numRating = (TextView) view.findViewById(R.id.save_place_num_star);
            openNow = (TextView) view.findViewById(R.id.save_place_open_now);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mOnClickItem != null){
                        mOnClickItem.onItemClick(v, getLayoutPosition(), homeItemLists);
                    }
                }
            });
        }


    }
}

