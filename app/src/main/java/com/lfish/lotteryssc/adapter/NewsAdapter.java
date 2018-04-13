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

import com.bumptech.glide.Glide;
import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.dao.News;
import com.lfish.lotteryssc.net.dao.YYResult;
import com.lfish.lotteryssc.utils.CornersTransform;
import com.lfish.lotteryssc.view.AutoNewLineLayout;

import java.util.List;

/**
 * Created by shenmegui on 2018/2/28.
 */
public class NewsAdapter extends BaseAdapter {

    List<News.New> news;

    LayoutInflater inflater;

    Context context;

    public NewsAdapter(Context context, List<News.New> news) {
        this.news = news;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_news,null);
        }

        ImageView ico = (ImageView) convertView.findViewById(R.id.iv_news_ico);
        TextView title = (TextView) convertView.findViewById(R.id.tv_news_title);
        TextView content = (TextView) convertView.findViewById(R.id.tv_news_content);

        News.New aNew = news.get(position);
        if(aNew.getImageUrls()!=null && aNew.getImageUrls().size()>0){
            Glide.with(context).load(aNew.getImageUrls().get(0)).transform(new CornersTransform(context,50)).into(ico);
        }

        title.setText(aNew.getTitle());
        content.setText(aNew.getContent());

        return convertView;
    }
}
