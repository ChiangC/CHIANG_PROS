package com.fmtech.fmlive.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.fmtech.fmlive.R;
import com.fmtech.fmlive.activity.LivePublisherActivity;
import com.fmtech.fmlive.util.Constants;
import com.tencent.upload.Const;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech All Rights Reserved.
 *
 * Created by Drew.Chiang on 17/8/9 16:19
 *
 * @version v1.0.0
 *
 * ==================================================================
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenRecordService extends Service {

    private static final String TAG = ScreenRecordService.class.getSimpleName();

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mExitBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mExitBroadcastReceiver = new ExitBroadcastRecevier();
        mLocalBroadcastManager.registerReceiver(mExitBroadcastReceiver, new IntentFilter(Constants.EXIT_APP));

        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, LivePublisherActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        builder.setContentIntent(contentIntent);
        builder.setTicker(getApplicationContext().getString(R.string.foreground_service_start));
        builder.setContentTitle(getApplicationContext().getString(R.string.recording_in_progress));
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mExitBroadcastReceiver);
    }

    public class ExitBroadcastRecevier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constants.EXIT_APP.equals(intent.getAction())){
                /*Intent restartIntent = new Intent(getApplicationContext(), TCScreenRecordActivity.class);
                restartIntent.setAction(TCConstants.EXIT_APP);
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(restartIntent);*/
            }
        }
    }

}
