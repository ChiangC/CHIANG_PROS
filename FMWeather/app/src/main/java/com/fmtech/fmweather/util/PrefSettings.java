package com.fmtech.fmweather.util;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/18 21:40
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class PrefSettings {

    public static final String CHANGE_ICONS = "change_icons";
    public static final String CLEAR_CACHE = "clear_cache";
    public static final String AUTO_UPDATE = "change_update_time";
    public static final String CITY_NAME = "城市";
    public static final String HOUR = "current_hour";
    public static final String NOTIFICATION_MODEL = "notification_model";

    public static int ONE_HOUR = 1000 * 60 * 60;

    private static PrefSettings sInstance;

    private PrefSettings(){

    }

    public PrefSettings getInstance(){
        if(null == sInstance){
            synchronized (sInstance){
                if(null == sInstance){
                    sInstance = new PrefSettings();
                }
            }
        }

        return sInstance;
    }


}
