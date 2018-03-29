package com.lfish.lotterysscjh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.lfish.lotterysscjh.adapter.HistoryAdapter;
import com.lfish.lotterysscjh.adapter.HistoryItemAdapter;
import com.lfish.lotterysscjh.dao.YYEnter;
import com.lfish.lotterysscjh.net.RetrofitManager;
import com.lfish.lotterysscjh.net.dao.Result;
import com.lfish.lotterysscjh.net.dao.YYResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

public class HistoryActivity extends AppCompatActivity {

    Spinner spinner;
    List<YYEnter> yyEnters;
    ListView hisList;
    public  static  int ChoosePos  = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        yyEnters = GoodsResultActivity.getYyEnters();
        spinner = (Spinner) findViewById(R.id.spinner1);
        hisList = (ListView) findViewById(R.id.ls_history);

        spinner.setAdapter(new HistoryAdapter(this,yyEnters));


        ActionBar supportActionBar = getSupportActionBar();
        // 显示返回按钮
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        // 去掉logo图标
        supportActionBar.setDisplayShowHomeEnabled(false);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getYYMsg(yyEnters.get(position).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(ChoosePos);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getYYMsg(String msgcode){
        showProgressDialog2();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        HashMap<String,String> params = new HashMap<>();
        params.put("code",msgcode);
        params.put("count",50+"");
        params.put("showapi_appid","57692");
        params.put("showapi_test_draft","false");
        params.put("showapi_timestamp",format.format(new Date(System.currentTimeMillis())));
        params.put("showapi_sign","bca648dc5285405987e4b4acb5329a73");

        RetrofitManager.getInstance().getYYMSG("https://route.showapi.com/44-2", params, new Subscriber<Result<YYResult>>() {
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

                hisList.setAdapter(new HistoryItemAdapter(HistoryActivity.this,yyResultResult.getShowapi_res_body().getResult()));

//                YYResult.YYRESULT_CHILD yyresult_child = yyResultResult.getShowapi_res_body().getResult().get(0);
//                goodsName.setText(yyresult_child.getName());
//                goodsTime.setText("开奖日期:"+yyresult_child.getTime());
//                goodsNumber.setText(yyresult_child.getOpenCode()+"");
//                except.setText("第"+yyresult_child.getExpect()+"期");


//                final ShowMsgDialog showMsgDialog =  new ShowMsgDialog(HistoryActivity.this,yyresult_child);
//                showMsgDialog.setYesOnclickListener("关闭", new ShowMsgDialog.onYesOnclickListener() {
//                    @Override
//                    public void onYesClick() {
//                        showMsgDialog.dismiss();
//                    }
//                });
//                showMsgDialog.show();
            }
        });



    }

    private ProgressDialog mDialog2;
    private void showProgressDialog2(){
        if(mDialog2==null){
            mDialog2 = new ProgressDialog(HistoryActivity.this);
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
