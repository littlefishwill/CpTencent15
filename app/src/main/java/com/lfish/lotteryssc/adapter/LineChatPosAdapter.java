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
public class LineChatPosAdapter extends BaseAdapter {

    int count;

    LayoutInflater inflater;


    public LineChatPosAdapter(Context context, int count) {
        this.count = count;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return count;
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
            convertView = inflater.inflate(R.layout.item_history_selected,null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_msg);

        textView.setText("第"+(position+1)+"位");

        return convertView;
    }
}
