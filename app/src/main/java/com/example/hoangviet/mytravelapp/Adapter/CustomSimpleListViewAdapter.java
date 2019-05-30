package com.example.hoangviet.mytravelapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoangviet.mytravelapp.ItemsList.HandBookItem;
import com.example.hoangviet.mytravelapp.R;

import java.util.List;

public class CustomSimpleListViewAdapter extends ArrayAdapter<HandBookItem> {
    private Context context;
    private int resource;
    private List<HandBookItem> handBookItemList;

    public CustomSimpleListViewAdapter(Context context, int resource, List<HandBookItem> handBookItemList) {
        super(context,resource,handBookItemList);
        this.context = context;
        this.resource = resource;
        this.handBookItemList = handBookItemList;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_handbook, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title_handbook);

            convertView.setTag(viewHolder);
        }
        else{

            viewHolder = (ViewHolder) convertView.getTag();
        }
        HandBookItem handBookItem = handBookItemList.get(position);

        viewHolder.title.setText(handBookItem.getTitle().toString());

        return convertView;


    }
    public class ViewHolder{
        TextView title;
    }
}
