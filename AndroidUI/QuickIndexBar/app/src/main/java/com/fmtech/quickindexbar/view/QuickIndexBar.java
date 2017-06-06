package com.fmtech.quickindexbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/6/15 17:12
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/6/15 17:12  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class QuickIndexBar extends View {

    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"};

    private Paint mPaint;
    private float mCellHeight;
    private int mCellWidth;
    private int mCurrY;
    private int mCurrIndex = -1;
    private int mTouchIndex = -2;
    private OnLetterSelectedListener mListener;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = 0.0f;
        float y = 0.0f;
        int textHeight;
        Rect bounds = new Rect();
        for(int i=0; i<LETTERS.length; i++){
            //measureText获取文本的宽度
            x = (mCellWidth*1.0f - mPaint.measureText(LETTERS[i])*1.0f)/2;
            //Returns the unioned bounds of all the text.
            mPaint.getTextBounds(LETTERS[i], 0, LETTERS[i].length(), bounds);
            //获取文本的高度
            textHeight = bounds.height();
            //绘制字母是在中间位置绘制，且是字母绘制区域的左下角为开始点绘制的
            y = (mCellHeight + textHeight)/2.0f + (mCellHeight)*i;
            mPaint.setColor((i == mTouchIndex)?Color.GRAY:Color.WHITE);
            canvas.drawText(LETTERS[i], x, y, mPaint);
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = getMeasuredWidth();
        int height = getMeasuredHeight();
        mCellHeight = height*1.0f/LETTERS.length;//每个字母应该占据的高度
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                //得到当前触点落在QuickIndexBar上时，所对应的字母的下标
                mCurrIndex = (int)(event.getY()/mCellHeight);
                if(mCurrIndex >= 0 && mCurrIndex < LETTERS.length && mCurrIndex != mTouchIndex){
                    mTouchIndex = mCurrIndex;
                    if(null != mListener){
                        mListener.onSelected(LETTERS[mCurrIndex]);
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                mCurrIndex = (int)(event.getY()/mCellHeight);
                if(mCurrIndex >= 0 && mCurrIndex < LETTERS.length && mCurrIndex != mTouchIndex){
                    mTouchIndex = mCurrIndex;
                    if(null != mListener){
                        mListener.onSelected(LETTERS[mCurrIndex]);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                mTouchIndex = -1;
                break;
        }
        invalidate();
        return true;
    }

    public interface OnLetterSelectedListener{
        void onSelected(String letter);
    }

    public void setOnLetterSelectedListener(OnLetterSelectedListener listener){
        mListener = listener;
    }

    public OnLetterSelectedListener getOnLetterSelectedListener(){
        return mListener;
    }
}
