package com.fmtech.chinamapsvg012.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2017/6/26 21:57
 * @description ${todo}
 * <p>
 * ==================================================================
 */


public class ProvinceItem {

    private Path path;

    private int drawColor;

    public ProvinceItem(Path path){
        this.path = path;
    }

    public void draw(Canvas canvas, Paint paint, boolean isSelected){

    }

    public boolean isTouch(int x, int y){

        return false;
    }
}
