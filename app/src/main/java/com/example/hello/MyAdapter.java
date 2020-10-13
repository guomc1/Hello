package com.example.hello;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter {


    public MyAdapter(Context context, int resource, List<Currency> list){
        super(context,resource,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;

        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,
                    parent,
                    false);
        }

        Currency currency = (Currency) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail);

        title.setText(currency.getName());
        detail.setText(currency.getRate());

        return itemView;
    }


}
