package com.lfish.lotteryssc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.dao.YYEnter;

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

        ImageView ico = (ImageView) convertView.findViewById(R.id.iv_history_ico);
        if(yyEnters.get(position).getDrawable()!=0) {
            ico.setImageResource(yyEnters.get(position).getDrawable());
            ico.setVisibility(View.VISIBLE);
        }else{
            ico.setVisibility(View.GONE);
        }
        return convertView;
    }
}
