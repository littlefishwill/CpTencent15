package com.lfish.lotteryssc;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.lfish.lotteryssc.dialog.ShowRadomDialog;

public class RadomChooseActivity extends AppCompatActivity {

    private String[] conStrs = new String[]{"恭喜发财,好运连连","狗年大吉，财源滚滚","一不小心中它一个亿","这几个数字666","财源广进，好运到","有预感好像要中奖","给你一万个祝福","就问你敢不敢下注","今年走大运","年年走大运","钱多多，长长久久"};

    private EditText name,age,gender;

    Button btn ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    closeProgressDialog2();
                    int rodoms1 =  (int)((Math.random()*9+1)*1);
                   int rodoms7 =  (int)((Math.random()*9+1)*1000000);

                    final ShowRadomDialog showRadomDialog = new ShowRadomDialog(RadomChooseActivity.this, conStrs[rodoms1], rodoms7 + "");
                    showRadomDialog.show();
                    showRadomDialog.setYesOnclickListener("关闭", new ShowRadomDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            showRadomDialog.dismiss();
                        }
                    });

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radom_choose);

        btn = (Button) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.et_name);
        age = (EditText) findViewById(R.id.et_age);
        gender = (EditText) findViewById(R.id.et_gender);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().length()<1){
                    Toast.makeText(RadomChooseActivity.this,"请输入姓名",Toast.LENGTH_LONG).show();
                    return;
                }

                if(age.getText().toString().length()<1){
                    Toast.makeText(RadomChooseActivity.this,"请输入年龄",Toast.LENGTH_LONG).show();
                    return;
                }

                if(gender.getText().toString().length()<1){
                    Toast.makeText(RadomChooseActivity.this,"请输入性别",Toast.LENGTH_LONG).show();
                    return;
                }

                showProgressDialog2();
                handler.sendEmptyMessageDelayed(0,3000);
            }
        });

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        CubeGrid doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);

    }


    private ProgressDialog mDialog2;
    private void showProgressDialog2(){
        if(mDialog2==null){
            mDialog2 = new ProgressDialog(RadomChooseActivity.this);
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
