package com.lfish.lotterysscjh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lfish.lotterysscjh.adapter.LocalImageHolderView;
import com.lfish.lotterysscjh.adapter.NewOpenCodeAdapter;
import com.lfish.lotterysscjh.dao.YYEnter;
import com.lfish.lotterysscjh.net.RetrofitManager;
import com.lfish.lotterysscjh.net.dao.Result;
import com.lfish.lotterysscjh.net.dao.YYResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

public class GoodsResultActivity extends AppCompatActivity {

    private ListView list;
    private static List<YYEnter> yyEnters = new ArrayList<YYEnter>();
    public static HashMap<String,YYEnter> yyEnterHashMap = new HashMap<>();
    private TextView goodsName,goodsTime,goodsNumber,except;
    private ConvenientBanner convenientBanner;
    private List<String> adAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_result);
        list = (ListView) findViewById(R.id.ls_list);
        goodsName = (TextView) findViewById(R.id.tv_name);
        goodsTime = (TextView) findViewById(R.id.tv_time);
        goodsNumber = (TextView) findViewById(R.id.tv_number);
        except = (TextView) findViewById(R.id.tv_except);

        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        convenientBanner.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        ActionBar actionBar = getActionBar();
//        actionBar.show();


        initYYEnter();
        getYYMsg(getMoreCode());
        initAds();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryActivity.ChoosePos = position;
                Intent intent = new Intent(GoodsResultActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initAds() {

        List<Integer> localImages = new ArrayList<Integer>();
        localImages.add(R.drawable.ad2);
        localImages.add(R.drawable.ad8);
        localImages.add(R.drawable.ad4);
//        localImages.add(R.drawable.ad3);

        adAddress = new ArrayList<String>();

        adAddress.add("http://m3.ttacp8.com/activity/group/viewPage.html?activityId=tgn13&from=cppcb");
        adAddress.add("http://www.qipaigame1.com/zjh_download.html?from=zy1");
        adAddress.add("http://fa.163.com/activity/s/AdviserWechat/qrcode/sign.do?from=tgnwxoe97ohx1");

        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ad_point_n, R.drawable.ad_point_s})
//                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

        convenientBanner.startTurning(2000);

        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WebActivity.WebUrl = adAddress.get(position);
                Intent intent = new Intent(GoodsResultActivity.this,WebActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getYYMsg(String msgcode){
        showProgressDialog2();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        HashMap<String,String> params = new HashMap<>();
        params.put("code",msgcode);
        params.put("showapi_appid","57692");
        params.put("showapi_test_draft","false");
        params.put("showapi_timestamp",format.format(new Date(System.currentTimeMillis())));
        params.put("showapi_sign","bca648dc5285405987e4b4acb5329a73");

        RetrofitManager.getInstance().getYYMSG("https://route.showapi.com/44-1", params, new Subscriber<Result<YYResult>>() {
            @Override
            public void onCompleted() {
                closeProgressDialog2();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<YYResult> yyResultResult) {
                Log.i("GetEd",yyResultResult.getShowapi_res_code()+"");
                list.setAdapter(new NewOpenCodeAdapter(GoodsResultActivity.this,yyResultResult.getShowapi_res_body().getResult()));
                closeProgressDialog2();
            }
        });
    }


    public static List<YYEnter> getYyEnters() {
        return yyEnters;
    }

    public static void initYYEnter (){

        if(yyEnters.size()>0){
            return;
        }

        addYYEnter(new YYEnter("时时彩","cqssc",R.drawable.cp_ico_cqssc));
        addYYEnter(new YYEnter("快乐10分","gdklsf",R.drawable.cp_ico_klsf));
        addYYEnter(new YYEnter("超级大乐透","dlt",R.drawable.cp_ico_dlt));
        addYYEnter(new YYEnter("福彩3d","fc3d",R.drawable.cp_ico_fc3d));
        addYYEnter(new YYEnter("排列3","pl3"));
        addYYEnter(new YYEnter("排列5","pl5"));
        addYYEnter(new YYEnter("七乐彩","qlc"));
        addYYEnter(new YYEnter("七星彩","qxc"));
        addYYEnter(new YYEnter("双色球","ssq"));
        addYYEnter(new YYEnter("六场半全场","zcbqc"));
        addYYEnter(new YYEnter("四场进球彩","zcjqc"));
        addYYEnter(new YYEnter("十四场胜负彩(任9)","zcsfc"));
        addYYEnter(new YYEnter("11选5","ah11x5"));
        addYYEnter(new YYEnter("1.5分彩","afgh90"));
        addYYEnter(new YYEnter("乐透快乐8","krnkl8"));
        addYYEnter(new YYEnter("官方快乐8","krkeno22"));

    }

    private static void addYYEnter(YYEnter yyEnter){
        yyEnters.add(yyEnter);
        yyEnterHashMap.put(yyEnter.getCode(),yyEnter);
    }

    public static String getMoreCode(){
        StringBuilder stringBuilder = new StringBuilder();
        for(YYEnter yyEnter:yyEnters){
            stringBuilder.append(yyEnter.getCode()+"|");
        }
        String s = stringBuilder.toString();
        if(s.endsWith("|")){
            s.substring(0,s.length()-1);
        }
        Log.i("???-1-1",s);
        return s;
    }

    private ProgressDialog mDialog2;
    private void showProgressDialog2(){
        if(mDialog2==null){
            mDialog2 = new ProgressDialog(GoodsResultActivity.this);
            mDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mDialog2.setMessage("正在加载 ，请等待...");
            mDialog2.setIndeterminate(false);//设置进度条是否为不明确
            mDialog2.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog2.setCanceledOnTouchOutside(false);
            mDialog2.setProgress(10);
            mDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog2=null;
                }
            });
            mDialog2.show();

        }
    }
    private void closeProgressDialog2(){
        if(mDialog2!=null){
            mDialog2.dismiss();
            mDialog2=null;
        }
    }

}
