package com.fmtech.fmweather.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fmtech.fmweather.MainActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/9 15:16
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class SplashActivity extends BaseActivity{

    private SplashHandler mHandler = new SplashHandler(Looper.getMainLooper(), SplashActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mHandler.sendEmptyMessageDelayed(0, 1000);
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        gotoMainPage();
                        return null;
                    }
                }).subscribe();
    }

    class SplashHandler extends Handler{
        private WeakReference<SplashActivity> mActivityRef;

        public SplashHandler(Looper looper, SplashActivity activity){
            super(looper);
            mActivityRef = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gotoMainPage();
        }
    }

    private void gotoMainPage(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        SplashActivity.this.finish();
    }
}
