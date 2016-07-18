package com.fmtech.fmweather.util;

import android.content.Context;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/18 21:21
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static Thread.UncaughtExceptionHandler sDefaultHandler = null;

    private Context mContext;

    public CrashHandler(Context context){
        mContext = context;
    }

    public static void init(CrashHandler crashHandler){
        sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println(ex.toString());

        sDefaultHandler.uncaughtException(thread, ex);
    }

    public String getCrashInfo(Throwable ex){
        Writer exInfoWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(exInfoWriter);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return exInfoWriter.toString();
    }

    public String collectCrashDeviceInfo(){
        String appVersionName = Utils.getVersion(mContext);
        String model = Build.MODEL;
        String androidVersion = Build.VERSION.RELEASE;
        String manufacturer = Build.MANUFACTURER;
        return appVersionName + "  " + model + "  " + androidVersion + "  " + manufacturer;
    }
}
