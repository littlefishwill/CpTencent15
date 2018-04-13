package com.lfish.lotteryssc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lfish.lotteryssc.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by shenmegui on 2018/2/28.
 */
public class PlaneAdapter extends BaseAdapter {

    List<List<Map.Entry<Integer, Integer>>> params;

    LayoutInflater inflater;


    public PlaneAdapter(Context context, List<List<Map.Entry<Integer, Integer>>> params) {
        this.params = params;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return params.size();
    }

    @Override
    public Object getItem(int position) {
        return params.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_plane,null);
        }

        TextView noDes = (TextView) convertView.findViewById(R.id.tv_plan_no_des);

        noDes.setText("第"+(position+1)+"位");


        TextView no = (TextView) convertView.findViewById(R.id.tv_plan_no);

        Map.Entry<Integer, Integer> integerIntegerEntry = params.get(position).get(0);

        no.setText(integerIntegerEntry.getKey()+"");

        TextView times = (TextView) convertView.findViewById(R.id.tv_plan_times);

        times.setText(integerIntegerEntry.getValue()+"/50");

        TextView present = (TextView) convertView.findViewById(R.id.tv_plan_present);

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float)integerIntegerEntry.getValue()/(float)50*100);
        present.setText(result+"%");

        return convertView;
    }
}
