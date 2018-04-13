package com.lfish.lotteryssc.net.novate;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求头的过滤器 header
 * Created by  wang on 2016/12/14.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        String time=System.currentTimeMillis()+"abc";
//        String aesCode= Encrypt.aesEncode("baimes123",time);
//        String result=time+aesCode;
//        Log.w("header","result--"+result);

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("X-apiversion", "1.0.0").
                addHeader("X-token", "8765432112345678").build();
        return chain.proceed(builder.build());
    }
}
