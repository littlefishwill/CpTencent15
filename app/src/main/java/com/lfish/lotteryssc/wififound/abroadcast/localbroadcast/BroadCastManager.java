package com.lfish.lotteryssc.wififound.abroadcast.localbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.gson.Gson;
import com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast.ParamsBroadCastReceiver;

/**
 * Created by shenmegui on 2016/10/11.
 */
public class BroadCastManager {

    private Context context;
    public void init(Context context) {
        this.context = context;
    }

    private static BroadCastManager broadCastManager;
    public static BroadCastManager getInstance(){
        if(broadCastManager ==null){
            broadCastManager = new BroadCastManager();
        }
        return broadCastManager;
    }

    private BroadCastManager() {

    }

    /**
     * 发送广播
     * @param broadCast
     */
    public void sendBroadCast(BroadCast broadCast){
        Intent intent = new Intent();
        intent.setAction(broadCast.getClass().toString());
        intent.putExtra(ParamsBroadCastReceiver.DefaultParamsName,new Gson().toJson(broadCast));
        context.sendBroadcast(intent);
    }

    /**
     * 注册广播
     * @param context
     * @param broadCast
     * @param broadcastReceiver
     */
    public void registerReceiver(Context context,BroadCast broadCast, BroadcastReceiver broadcastReceiver){
        IntentFilter filter = new IntentFilter();
        filter.addAction(broadCast.getClass().toString());
        filter.setPriority(Integer.MAX_VALUE);
        try{
            context.registerReceiver(broadcastReceiver,filter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 取消注册广播
     * @param context
     * @param broadcastReceiver
     */
    public void unRegisterReceiver(Context context, BroadcastReceiver broadcastReceiver){
        context.unregisterReceiver(broadcastReceiver);
    }

}
