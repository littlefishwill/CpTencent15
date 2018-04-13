package com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 发送带参数的广播
 * Created by shenmegui on 2016/10/26.
 */
public abstract class ParamsBroadCastReceiver<T extends UdpBroadCast> extends BroadcastReceiver {
    public static final String DefaultParamsName = "params";
    private Class<T> cls = null;
    @Override
    public void onReceive(Context context, Intent intent) {

        String stringExtra = intent.getStringExtra(DefaultParamsName);
        //为了得到T的Class，采用如下方法
        //1得到该泛型类的子类对象的Class对象
        Class clz = this.getClass();
        //2得到子类对象的泛型父类类型（也就是BaseDaoImpl<T>）
        ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
        //
        Type[] types = type.getActualTypeArguments();
        cls = (Class<T>) types[0];
        UdpBroadCast o = new Gson().fromJson(stringExtra, cls);

        if(o.getBroadVersion()!= UdpBroadCastManager.LANBROAD_VERSION){
            Log.i("ParamsBroadCastReceiver","局域网内检测到版本号不一致的广播 版本："+o.getBroadVersion());
            return;
        }
        onReveive(context, (T) o);
    }

    public abstract void onReveive(Context context, T params);
}
