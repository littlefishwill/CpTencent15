package com.lfish.lotterysscjh.net.novate;

import android.util.Log;

import com.google.gson.Gson;

import rx.Subscriber;

/**
 * Created by wang on 2016/9/18.
 */
public abstract class CommentSubscriber <T> extends Subscriber <T> {

    @Override
    public void onCompleted() {
        Log.e("http","onCompleted()--");
    }

    @Override
    public void onNext(T t) {
        Log.e("http",  new Gson().toJson(t));
    }

    @Override
    public void onStart() {
        Log.e("http","onStart()--");
    }
}
