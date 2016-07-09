package com.fmtech.fmweather;

import android.app.Application;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/9 17:26
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class FMApplication extends Application{

    private static FMApplication mAppContext;

    public FMApplication getAppContext(){
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;


    }
}
