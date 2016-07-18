package com.fmtech.fmweather;

import android.app.Application;

import com.fmtech.fmweather.common.AppBlockCanaryContext;
import com.fmtech.fmweather.util.CrashHandler;
import com.fmtech.fmweather.util.PreferenceUtils;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

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

        PreferenceUtils.init(getAppContext());

        CrashHandler.init(new CrashHandler(getAppContext()));

        CrashReport.initCrashReport(getApplicationContext(), "900028220", false);

//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        LeakCanary.install(this);


    }
}
