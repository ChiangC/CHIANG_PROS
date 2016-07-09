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

public class SplashActivity extends AppCompatActivity{

    private SplashHandler mHandler = new SplashHandler(Looper.getMainLooper(), SplashActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler.sendEmptyMessageDelayed(0, 1000);
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
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            SplashActivity.this.finish();
        }
    }
}
