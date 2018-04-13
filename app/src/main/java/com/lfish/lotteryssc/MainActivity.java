package com.lfish.lotteryssc;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lfish.lotteryssc.net.RetrofitManager;
import com.lfish.lotteryssc.net.dao.ResultSwitch;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    RetrofitManager.getInstance().getSwitch(Config.getString(), new Subscriber<ResultSwitch>() {
                        @Override
                        public void onCompleted() {
                            finish();
                        }
                        @Override
                        public void onError(Throwable e) {
                            Intent intent = new Intent(MainActivity.this,MainGroup.class);
                            startActivity(intent);
                            finish();
                            Log.e("???",e.getMessage());
                        }
                        @Override
                        public void onNext(ResultSwitch resultSwitchResult) {
                            Log.i("?????",resultSwitchResult.getWapurl());
                            if(resultSwitchResult!=null && resultSwitchResult.getIsshowwap().equals("1")){
                                ReadyUpdateActivity.url = resultSwitchResult.getWapurl();
//                                ReadyUpdateActivity.url = "http://imtt.dd.qq.com/16891/DC3652E9C7A882C2C5D3E53B8D7AC3AE.apk?fsname=cn.ssces.apps.yyb_1.0_1.apk&amp;csr=1bbd";
                                Intent intent = new Intent(MainActivity.this,ReadyUpdateActivity.class);
                                startActivity(intent);

                            }else{
                                Intent intent = new Intent(MainActivity.this,MainGroup.class);
                                startActivity(intent);
                            }
                        }
                    });
                break;
                case 1:
                    Intent intent = new Intent(MainActivity.this,MainGroup.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Config.isReady()){
            handler.sendEmptyMessageDelayed(0, 1000);
        }else{
            Log.i("???",System.currentTimeMillis()+"");
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    }
}
