package com.fmtech.fmlive.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/1/11 08:55
 * <p>
 * ==================================================================
 */


public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Handler mHanlder = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBeforeLayout();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;

        if(getLayoutResId() > 0){
            setContentView(getLayoutResId());
            initViews();
            initData();
            setupListeners();
        }

    }

    protected void setBeforeLayout(){

    }

    protected abstract int getLayoutResId();


    protected abstract void initViews();


    protected abstract void initData();


    protected abstract void setupListeners();


    public <T extends View> T obtainView(int resId){
        return (T)findViewById(resId);
    }


    public Toast showToast(int resId){
        return showToast(getString(resId));
    }

    public Toast showToast(String message){
        if(TextUtils.isEmpty(message)){
            return null;
        }

        final Toast toast = Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT);
        mHanlder.post(new Runnable() {
            @Override
            public void run() {
                toast.show();
            }
        });
        
        return toast;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
