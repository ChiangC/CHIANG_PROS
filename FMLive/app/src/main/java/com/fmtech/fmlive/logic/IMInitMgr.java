package com.fmtech.fmlive.logic;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.fmtech.fmlive.base.TCConstants;
import com.tencent.TIMGroupSettings;
import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/2/9 18:00
 * <p>
 * ==================================================================
 */


public class IMInitMgr {
    private static boolean isSDKInit = false;

    /**
     * IMSDK init操作
     * @param context application context
     */
    public static void init(final Context context) {

        if (isSDKInit)
            return;

        //禁止服务器自动代替上报已读
        TIMManager.getInstance().disableAutoReport();
        //初始化imsdk
        TIMManager.getInstance().init(context);
        //初始化群设置
        TIMManager.getInstance().initGroupSettings(new TIMGroupSettings());
        //注册sig失效监听回调
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(new Intent(TCConstants.EXIT_APP));
            }

            @Override
            public void onUserSigExpired() {
                LoginMgr.getInstance().reLogin();
            }
        });

        //初始化登录模块
        LoginMgr.getInstance().init(context);
        //初始化注册模块
//        RegisterMgr.getInstance().init(context);
        //禁用消息存储
        //TIMManager.getInstance().disableStorage();

        isSDKInit = true;
    }
}
