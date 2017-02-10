package com.fmtech.fmlive.base;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.fmtech.fmlive.util.Utils;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/2/10 10:53
 * <p>
 * ==================================================================
 */


public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context){
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return Utils.createCircleImage(toTransform, 0);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
