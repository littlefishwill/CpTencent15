package com.lfish.lotterysscjh;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.lfish.lotterysscjh.adapter.HistoryAdapter;
import com.lfish.lotterysscjh.adapter.PlaneAdapter;
import com.lfish.lotterysscjh.dao.YYEnter;
import com.lfish.lotterysscjh.net.RetrofitManager;
import com.lfish.lotterysscjh.net.dao.Result;
import com.lfish.lotterysscjh.net.dao.YYResult;
import com.lfish.lotterysscjh.view.AutoNewLineLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import rx.Subscriber;

public class PlaneNumberActivity extends AppCompatActivity {

    Spinner spinner,posSpinner;
    List<YYEnter> yyEnters;
    AutoNewLineLayout autoNewLineLayout;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planenumber);
        yyEnters = GoodsResultActivity.getYyEnters();
        listView = (ListView) findViewById(R.id.ls_planes);
        spinner = (Spinner) findViewById(R.id.spinner1);
        posSpinner = (Spinner) findViewById(R.id.spinner2);

        autoNewLineLayout = (AutoNewLineLayout) findViewById(R.id.anl_plan_numbers);

        spinner.setAdapter(new HistoryAdapter(this,yyEnters));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getYYMsg(yyEnters.get(position).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_plane_header,null));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNumbers(String [] numbers){
        autoNewLineLayout.removeAllViews();
        boolean isred = true;
        for(String num:numbers){
            TextView tvNumber = new TextView(PlaneNumberActivity.this);
            tvNumber.setText(num);
//            tvNumber.setLayoutParams(params);
            if(isred) {
                tvNumber.setBackground(getResources().getDrawable(R.drawable.shape_number_red_circle));
            }else{
                tvNumber.setBackground(getResources().getDrawable(R.drawable.shape_number_blue_circle));
            }
            tvNumber.setGravity(Gravity.CENTER);
            tvNumber.setTextSize(15);
            if(num.contains("+")){
                isred = false;
                String[] split = num.split("\\+");
                tvNumber.setText(split[0]);
                autoNewLineLayout.addView(tvNumber);

                TextView tvNumber2 = new TextView(PlaneNumberActivity.this);
                tvNumber2.setText(split[1]);
                tvNumber2.setBackground(getResources().getDrawable(R.drawable.shape_number_blue_circle));
                tvNumber2.setGravity(Gravity.CENTER);

                autoNewLineLayout.addView(tvNumber2);
            }else{
                autoNewLineLayout.addView(tvNumber);
            }

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

//                ArrayList<YYResult.YYRESULT_CHILD> result = yyResultResult.getShowapi_res_body().getResult();

                final YYResult showapi_res_body = yyResultResult.getShowapi_res_body();

//                drawLineChat(showapi_res_body);

                new AsyncTask<Void,Void,Void>(){

                    Integer[][] data = null;
                    ArrayList<Entry> values1 = new ArrayList<>();
                    ArrayList<String> times = new ArrayList<String>();
                    @Override
                    protected Void doInBackground(Void... params) {
                        for(int i = 0;i<showapi_res_body.getResult().size();i++){
                            String[] split = showapi_res_body.getResult().get(i).getOpenCode().split(",|\\+");
                            times.add(showapi_res_body.getResult().get(i).getTime());
                            if(data==null){
                                data = new Integer[split.length][showapi_res_body.getResult().size()];
                                System.out.println(split.length+"?"+showapi_res_body.getResult().size());
                            }

                            for(int j =0;j < split.length;j++){
                                data[j][i] = Integer.parseInt(split[j]);
                            }
                        }

                        Log.i("???--?>????-",values1.size()+"");

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if(data== null || data.length<1){
                            return;
                        }else {
//                            posSpinner.setAdapter(new LineChatPosAdapter(PlaneNumberActivity.this, data.length));
                            drawLineChatPos(data,times);

                        }
                    }
                }.execute();

            }
        });

    }

    public void drawLineChatPos(Integer[][] data, final ArrayList<String> times){
        List<Entry> datas = new ArrayList<>();
        String[] numbers = new String[data.length];
        closeProgressDialog2();
        List<List<Map.Entry<Integer, Integer>>> params = new LinkedList<>();
        for(int i = 0;i<data.length;i++) {
            Map<Integer,Integer> present = new TreeMap<Integer,Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });


            for (int x = 0; x < data[i].length; x++) {
                float x1 = data[i][x];
                float y1 = x;

                datas.add(new Entry(y1, x1));

                Integer integer = present.get(data[i][x]);
                if (integer == null) {
                    integer = 0;
                }

                Log.i("Parmas",data[i][x]+":"+(integer + 1));

                present.put(data[i][x], integer + 1);
            }

            List<Map.Entry<Integer, Integer>> result = new LinkedList<>(present.entrySet());

            Collections.sort(result, new Comparator<Map.Entry<Integer, Integer>>() {
                @Override
                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            numbers[i] = result.get(0).getKey()+"";

            params.add(result);
        }

        listView.setAdapter(new PlaneAdapter(PlaneNumberActivity.this,params));

        showNumbers(numbers);

//        drawLineChat(data,datas,times);
//        drawPieChat(present);
    }

    private ProgressDialog mDialog2;
    private void showProgressDialog2(){
        if(mDialog2==null){
            mDialog2 = new ProgressDialog(PlaneNumberActivity.this);
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
