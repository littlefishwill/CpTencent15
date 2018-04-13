package com.lfish.lotteryssc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lfish.lotteryssc.R;

/**
 * Created by shenmegui on 2018/2/28.
 */
public class NumbersAdapter extends BaseAdapter {

    LayoutInflater inflater;
    String[] str;

    public NumbersAdapter(Context context, String str) {
        this.str = str.split(",");
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_numbers,null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_item_number_des);

        textView.setBackgroundResource(R.drawable.circle);
        textView.setText(str[position]);


        return convertView;
    }
}
