package com.fmtech.fmweather.network;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/9 14:48
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class WebService {

    private static WebService sInstance;

    private WebService(){

    }

    public static WebService instance(){
        if(null == sInstance){
            synchronized (sInstance){
                if(null == sInstance){
                    sInstance = new WebService();
                }
            }
        }
        return sInstance;
    }


}
