package com.lfish.lotteryssc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.lfish.lotteryssc.adapter.NewsAdapter;
import com.lfish.lotteryssc.dao.News;
import com.lfish.lotteryssc.net.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class NewsActivity extends AppCompatActivity {

    ListView listView;
    int page = 0;
    private String pageToken = null;
    private List<News.New> data = new ArrayList<>();
    private NewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = (ListView) findViewById(R.id.lv_news);

        View inflate = LayoutInflater.from(this).inflate(R.layout.item_bottom, null);

        listView.addFooterView(inflate);

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNews();
            }
        });

        getNews();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsActivity.this,UpdateActivity.class);
                UpdateActivity.WebUrl = data.get(position).getUrl();
                startActivity(intent);
            }
        });

    }

    public void getNews(){
        RetrofitManager.getInstance().getNews(Config.getNewsAddress(pageToken), new Subscriber<News>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                Intent intent = new Intent(NewsActivity.this,MainGroup.class);
                startActivity(intent);
                finish();
                Log.e("???",e.getMessage());
            }
            @Override
            public void onNext(News news) {
                pageToken = news.getPageToken();
                data.addAll(news.getData());
                if(adapter==null){
                    adapter = new NewsAdapter(NewsActivity.this,data);
                    listView.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
