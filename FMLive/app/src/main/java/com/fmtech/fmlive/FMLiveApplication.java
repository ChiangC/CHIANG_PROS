package com.fmtech.fmlive;

import android.app.Application;
import android.util.Log;
import com.tencent.rtmp.TXLivePusher;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/1/10 19:49
 * <p>
 * ==================================================================
 */


public class FMLiveApplication extends Application {

    private static FMLiveApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initLiveSDK();

    }

    public static FMLiveApplication getApplication() {
        return sInstance;
    }


    private void initLiveSDK(){
        int[] sdkver = TXLivePusher.getSDKVersion();
        if (sdkver != null && sdkver.length >= 3) {
            Log.d("rtmpsdk","rtmp sdk version is:" + sdkver[0] + "." + sdkver[1] + "." + sdkver[2]);
        }
    }
}
