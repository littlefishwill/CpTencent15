package com.lfish.lotteryssc.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.net.dao.YYResult;
import com.lfish.lotteryssc.view.AutoNewLineLayout;

import java.util.List;

/**
 * Created by shenmegui on 2018/2/28.
 */
public class HistoryItemAdapter extends BaseAdapter {

    List<YYResult.YYRESULT_CHILD> yyEnters;

    LayoutInflater inflater;

    Context context;

    public HistoryItemAdapter(Context context, List<YYResult.YYRESULT_CHILD> yyEnters) {
        this.yyEnters = yyEnters;
        inflater = LayoutInflater.from(context);
        this.context = context;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_newopencode_every,null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_exc);

        TextView times = (TextView) convertView.findViewById(R.id.tv_time);
        TextView number = (TextView) convertView.findViewById(R.id.tv_number);
        ImageView cpIco = (ImageView) convertView.findViewById(R.id.cp_ico);

        textView.setText("奖期:"+yyEnters.get(position).getExpect());

        times.setText(""+yyEnters.get(position).getTime());

        number.setText(""+yyEnters.get(position).getOpenCode());


//        YYEnter yyEnter = GoodsResultActivity.yyEnterHashMap.get(yyEnters.get(position).getCode());
//        if(yyEnter!=null && yyEnter.getDrawable()>0){
//            cpIco.setImageResource(yyEnter.getDrawable());
//            cpIco.setVisibility(View.VISIBLE);
//        }else{
            cpIco.setVisibility(View.GONE);
//        }

        AutoNewLineLayout linearLayout = (AutoNewLineLayout) convertView.findViewById(R.id.anl_numbers);
        linearLayout.removeAllViews();

        String[] numbers = yyEnters.get(position).getOpenCode().split(",");
        boolean isred = true;
        for(String num:numbers){
            TextView tvNumber = new TextView(context);
            tvNumber.setText(num);
//            tvNumber.setLayoutParams(params);
            if(isred) {
                tvNumber.setBackground(context.getDrawable(R.drawable.shape_number_red_circle));
            }else{
                tvNumber.setBackground(context.getDrawable(R.drawable.shape_number_blue_circle));
            }
            tvNumber.setGravity(Gravity.CENTER);
            tvNumber.setTextSize(15);
            if(num.contains("+")){
                isred = false;
                String[] split = num.split("\\+");
                tvNumber.setText(split[0]);
                linearLayout.addView(tvNumber);

                TextView tvNumber2 = new TextView(context);
                tvNumber2.setText(split[1]);
                tvNumber2.setBackground(context.getDrawable(R.drawable.shape_number_blue_circle));
                tvNumber2.setGravity(Gravity.CENTER);
                tvNumber2.setTextSize(15);

                linearLayout.addView(tvNumber2);
            }else{
                linearLayout.addView(tvNumber);
            }
        }

        return convertView;
    }
}
