package com.lfish.lotterysscjh;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lfish.lotterysscjh.net.RetrofitManager;
import com.lfish.lotterysscjh.net.dao.ResultSwitch;

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
                        }

                        @Override
                        public void onNext(ResultSwitch resultSwitchResult) {

                            if(resultSwitchResult!=null && resultSwitchResult.getIsshowwap().equals("1")){
                                WebActivity.WebUrl = resultSwitchResult.getWapurl();
                                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(MainActivity.this,MainGroup.class);
                                startActivity(intent);
                            }

                        }
                    });
                break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("???",System.currentTimeMillis()+"");

        if(Config.isReady()) {
            handler.sendEmptyMessageDelayed(0, 1000);
        }else{
            Toast.makeText(MainActivity.this,"软件过期",Toast.LENGTH_LONG).show();
        }

    }
}
