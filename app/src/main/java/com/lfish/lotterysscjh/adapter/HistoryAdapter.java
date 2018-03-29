package com.lfish.lotterysscjh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lfish.lotterysscjh.R;
import com.lfish.lotterysscjh.dao.YYEnter;

import java.util.List;

/**
 * Created by shenmegui on 2018/2/28.
 */
public class HistoryAdapter extends BaseAdapter {

    List<YYEnter> yyEnters ;

    LayoutInflater inflater;


    public HistoryAdapter(Context context,List<YYEnter> yyEnters) {
        this.yyEnters = yyEnters;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return yyEnters.size();
    }

    @Override
    public Object getItem(int position) {
        return yyEnters.get(position);
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

        textView.setText(yyEnters.get(position).getName());

        return convertView;
    }
}
