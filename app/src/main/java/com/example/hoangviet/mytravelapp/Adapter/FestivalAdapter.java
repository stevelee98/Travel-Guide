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
import com.example.hoangviet.mytravelapp.FireBaseRealTimeDataBase.FestivalItem;
import com.example.hoangviet.mytravelapp.R;

import java.util.List;

public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.MyViewHolder> {
    private Context context;
    private List<FestivalItem> festivalItemList;
    private static OnItemClickListener mOnClickItem;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnClickItem) {
        this.mOnClickItem = mOnClickItem;
    }

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
        myViewHolder.day.setText(item.getDay());

        Glide.with(context).load(item.getImgList().get(1).toString()).into(myViewHolder.avatar);

    }

    @Override
    public int getItemCount() {
        return festivalItemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView Name;
        public ImageView avatar;
        public TextView day;

        public MyViewHolder(final View view){
            super(view);

            Name = (TextView) view.findViewById(R.id.fes_name);
            avatar = (ImageView) view.findViewById(R.id.fes_thumbnail);
            day = (TextView) view.findViewById(R.id.day_fes);
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
