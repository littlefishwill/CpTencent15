package com.lfish.lotteryssc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.lfish.lotteryssc.Config;
import com.lfish.lotteryssc.MainGroup;
import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.adapter.NewsAdapter;
import com.lfish.lotteryssc.dao.News;
import com.lfish.lotteryssc.net.RetrofitManager;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * Created by shenmegui on 2018/4/12.
 */
public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_news,null);
        listView = (ListView) view.findViewById(R.id.lv_news);

        View inflate = inflater.inflate(R.layout.item_bottom, null);

        listView.addFooterView(inflate);

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNews();
            }
        });

        getNews();
        return view;
    }

    ListView listView;
    int page = 0;
    private String pageToken = null;
    private List<News.New> data = new ArrayList<>();
    private NewsAdapter adapter;


    public void getNews(){
        RetrofitManager.getInstance().getNews(Config.getNewsAddress(pageToken), new Subscriber<News>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                Intent intent = new Intent(getActivity(),MainGroup.class);
                startActivity(intent);
                Log.e("???",e.getMessage());
            }
            @Override
            public void onNext(News news) {
                pageToken = news.getPageToken();
                data.addAll(news.getData());
                if(adapter==null){
                    adapter = new NewsAdapter(getActivity(),data);
                    listView.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
