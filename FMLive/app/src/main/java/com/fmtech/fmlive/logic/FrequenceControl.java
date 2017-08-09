package com.fmtech.fmlive.logic;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech All Rights Reserved.
 *
 * Created by Drew.Chiang on 17/8/9 16:04
 *
 * @version v1.0.0
 *
 * ==================================================================
 */


public class FrequenceControl {

    private int mLimitCounts = 0;
    private int mSeconds = 0;
    private int mCurrentCounts = 0;
    private long mFirstTriggerTime = 0;

    public void init(int count, int seconds){
        mLimitCounts = count;
        mSeconds = seconds;
        mCurrentCounts = 0;
        mFirstTriggerTime = 0;
    }

    public boolean canTrigger(){

        long currentTime = System.currentTimeMillis();

        if(mFirstTriggerTime == 0 || currentTime - mFirstTriggerTime > 1000 * mSeconds){
            mFirstTriggerTime = currentTime;
            mCurrentCounts = 0;
        }

        if(mCurrentCounts >= mLimitCounts){
            return false;
        }

        ++mCurrentCounts;

        return true;
    }
}
