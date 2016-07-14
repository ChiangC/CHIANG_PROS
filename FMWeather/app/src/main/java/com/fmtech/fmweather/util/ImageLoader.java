package com.fmtech.fmweather.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/14 23:42
 * @description
 * Design to support Glide, Piccaso and Fresco.
 * <p/>
 * ==================================================================
 */

public class ImageLoader {

    public static void load(Context context, @DrawableRes int imageResId, ImageView imageView){
        Glide.with(context).load(imageResId).crossFade().into(imageView);
    }

    public static void clear(Context context){
        Glide.get(context).clearMemory();
    }
}
