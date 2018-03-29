package com.lfish.lotterysscjh;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lfish.lotterysscjh.dao.TabDao;

import java.util.ArrayList;
import java.util.List;

public class MainGroup extends AppCompatActivity {
    private ArrayList<CustomTabEntity> tabDaos = new ArrayList<>();
    private List<View> views=new ArrayList<>();
    private LocalActivityManager manager;
    private ViewPager vp_view_page_image;
    private CommonTabLayout slidingTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        titles.add("最新开奖结果");
//        titles.add("智能随机选");
//        titles.add("历史开奖查询");
//        titles.add("历史开奖查询");
//        titles.add("历史开奖查询");

//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();


//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().invalidate();

        setContentView(R.layout.activity_main_group);
        vp_view_page_image = (ViewPager) findViewById(R.id.viewpager);

        slidingTabLayout = (CommonTabLayout) findViewById(R.id.tabs);

        manager = new LocalActivityManager(this,true);
        manager.dispatchCreate(savedInstanceState);//必须写上这一行代码，不然会报错

        Intent intentItem = new Intent(getApplicationContext(),GoodsResultActivity.class);//这个类的第一个参数是上下文，第二个参数是你需要转化的Activity
        views.add(manager.startActivity("ItemActivity",intentItem).getDecorView());//将Activity转化为View然后放入View集合

        Intent intentPlane = new Intent(getApplicationContext(),PlaneNumberActivity.class);//这个类的第一个参数是上下文，第二个参数是你需要转化的Activity
        views.add(manager.startActivity("planActivity",intentPlane).getDecorView());//将Activity转化为View然后放入View集合
//
//        Intent intentThrid =new Intent(getApplicationContext(),HistoryActivity.class);
//        views.add(manager.startActivity("ExpandableActivity",intentThrid).getDecorView());

        Intent wifiExpandable =new Intent(getApplicationContext(),PieChartActivity.class);
        views.add(manager.startActivity("13213",wifiExpandable).getDecorView());

        Intent lineIntent =new Intent(getApplicationContext(),SettingActivity.class);
        views.add(manager.startActivity("3434343",lineIntent).getDecorView());

//        Intent intentExpandable =new Intent(getApplicationContext(),RadomChooseActivity.class);
//        views.add(manager.startActivity("ExpandableActivity",intentExpandable).getDecorView());
        vp_view_page_image.setAdapter(new MyAdapter());
//        slidingTabLayout.setTabData(new String[]{"最新开奖","历史开奖","走势图","共享彩"});

        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_view_page_image.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        tabDaos.add(new TabDao("开奖",R.drawable.ico_new_n,R.drawable.ico_new_s));
//        tabDaos.add(new TabDao("开奖记录",R.drawable.ico_history_n,R.drawable.ico_history_s));
        tabDaos.add(new TabDao("计划",R.drawable.ico_history_n,R.drawable.ico_history_s));
        tabDaos.add(new TabDao("走势",R.drawable.ico_lev_n,R.drawable.ico_lev_s));
        tabDaos.add(new TabDao("我的",R.drawable.ico_wd_n,R.drawable.ico_wd_s));

        vp_view_page_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        slidingTabLayout.setTabData(tabDaos);

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v=views.get(position);
            container.addView(v);

            return v;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabDaos.get(position).getTabTitle();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v=views.get(position);
            container.removeView(v);
        }


    }
}
