package com.fmtech.fmlive.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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


public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Handler mHanlder = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBeforeLayout();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;

    }

    protected void setBeforeLayout(){

    }

    public <T extends View> T obtainView(int resId){
        return (T)findViewById(resId);
    }
}
