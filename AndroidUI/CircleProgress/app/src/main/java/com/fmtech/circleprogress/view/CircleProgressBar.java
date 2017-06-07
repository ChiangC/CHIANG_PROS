package com.fmtech.circleprogress.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.fmtech.circleprogress.R;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 *          <p>
 *          ==================================================================
 */


public class CircleProgressBar extends View {
    private Paint mPaint;
    private int mDefaultCircleColor = Color.parseColor("#d3d3d3");
    private int mDefaultProgressColor = Color.parseColor("#04bce4");

    private int mCircleColor;
    private int mProgressColor;
    private int mMaxProgress;
    private float mCircleStrokeWidth;
    private int mTextColor;
    private float mTextSize;
    private int mProgress = 0;
    private float mRadius;
    private float mHalfWidth;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        mCircleColor = typedArray.getColor(R.styleable.CircleProgressBar_circleColor, mDefaultCircleColor);
        mProgressColor = typedArray.getColor(R.styleable.CircleProgressBar_progressColor, mDefaultProgressColor);
        mMaxProgress = typedArray.getInt(R.styleable.CircleProgressBar_maxProgress, 100);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mCircleStrokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_circleStrokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, displayMetrics));
        mTextColor = typedArray.getColor(R.styleable.CircleProgressBar_textColor, mDefaultProgressColor);
        mTextSize = typedArray.getDimension(R.styleable.CircleProgressBar_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, displayMetrics));
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mHalfWidth = getWidth() / 2;
        mRadius = mHalfWidth - mCircleStrokeWidth / 2;

        mPaint = new Paint();

        mPaint.setColor(mCircleColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleStrokeWidth);
        canvas.drawCircle(mHalfWidth, mHalfWidth, mRadius, mPaint);

        mPaint.reset();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setAntiAlias(true);
//        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        String percent = (mProgress * 100 / mMaxProgress) + "%";
        float textWidth = mPaint.measureText(percent);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseline = mHalfWidth + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
//        canvas.drawText(percent, (mHalfWidth - textWidth/2), mHalfWidth + (fontMetrics.bottom - fontMetrics.top)/2, mPaint);
        canvas.drawText(percent, (mHalfWidth - textWidth / 2), baseline, mPaint);

        drawProgress(canvas);

    }

    private void drawProgress(Canvas canvas) {
        mPaint.reset();
        mPaint.setColor(mProgressColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mCircleStrokeWidth);
        RectF oval = new RectF(mHalfWidth - mRadius, mHalfWidth - mRadius, mHalfWidth + mRadius, mHalfWidth + mRadius);
        canvas.drawArc(oval, -90f, mProgress * 360f / mMaxProgress, false, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    public void setProgress(int progress) {
        if(progress < 0){
            return;
        }
        if(progress > mMaxProgress){
            mProgress = mMaxProgress;
        }else{
            mProgress = progress;
        }
        invalidate();
    }

}
