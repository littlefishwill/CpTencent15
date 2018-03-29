package com.lfish.lotterysscjh.net.novate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lfish.lotterysscjh.net.novate.util.NetworkUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * caheInterceptor
 * Created by Tamic on 2016-08-09.
 */
public class CaheInterceptor implements Interceptor {

    private Context context;
    private String cacheControlValue;
    private String cacheOnlineControlValue;
    //set cahe times is 3 days
    private static final int maxStale = 60 * 60 * 24 * 3;
    // read from cache for 60 s
    private static final int maxOnlineStale = 60;

    public CaheInterceptor(Context context) {
        this(context, String.format("max-stale=%d", maxOnlineStale));
    }

    public CaheInterceptor(Context context, String cacheControlValue) {
        this(context, cacheControlValue, String.format("max-stale=%d", maxStale));
    }

    public CaheInterceptor(Context context, String cacheControlValue, String cacheOnlineControlValue) {
        this.context = context;
        this.cacheControlValue = cacheControlValue;
        this.cacheOnlineControlValue = cacheOnlineControlValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (NetworkUtil.isNetworkAvailable(context)) {
            Log.i("Tamic", maxOnlineStale + "load cahe pre:"+request.url());
            Response response = null;
            try {
                 response = chain.proceed(request);
            }catch (SocketTimeoutException s){
                Log.e("Tamic",request.url()+":SocketTimeoutException 访问超时");
                return null;
            }
            String cacheControl = request.cacheControl().toString();
            Log.i("Tamic", maxOnlineStale + "load cahe success" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, " + cacheOnlineControlValue)
                    .build();
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "当前无网络! 为你智能加载缓存", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e("Tamic", " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, " + cacheControlValue)
                    .build();
        }
    }
}
