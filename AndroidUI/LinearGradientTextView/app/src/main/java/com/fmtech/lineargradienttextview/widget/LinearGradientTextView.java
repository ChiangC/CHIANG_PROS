package com.fmtech.lineargradienttextview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2017/6/8 21:46
 * @description ${todo}
 * <p>
 * ==================================================================
 */


public class LinearGradientTextView extends TextView {

    private TextPaint mTextPaint;
    private LinearGradient mLinearGradient;

    public LinearGradientTextView(Context context) {
        super(context);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTextPaint = getPaint();

        mLinearGradient = new LinearGradient();
        mTextPaint.setShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
