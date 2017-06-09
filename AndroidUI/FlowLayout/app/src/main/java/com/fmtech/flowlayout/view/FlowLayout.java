package com.fmtech.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2017/6/4 18:34
 * @description ${todo}
 * <p>
 * ==================================================================
 */


public class FlowLayout extends ViewGroup {

    private List<Line> mLines;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mLines = new ArrayList<>();

        final int totalWidth = widthSize;
        int childCount = getChildCount();

        int parentHorizontalPadding = getPaddingLeft() + getPaddingRight();
        int widthUsed = parentHorizontalPadding;
        Line line = new Line();
        int i = 0;

        for(; i < childCount; i++){
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidthAndHorizontalMargin = (layoutParams.leftMargin + layoutParams.rightMargin + child.getMeasuredWidth());

            if((widthUsed + childWidthAndHorizontalMargin) > totalWidth){
                mLines.add(line);

                // new line
                line = new Line();
                line.addChild(child);

                widthUsed = parentHorizontalPadding;
                widthUsed += childWidthAndHorizontalMargin;

            }else{
                line.addChild(child);
                widthUsed += childWidthAndHorizontalMargin;
            }
        }
        if(i == childCount && line.getChildCount() > 0 && !mLines.contains(line)){
            mLines.add(line);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size = mLines.size();

        int lineHeight = 0;
        int childLeft = l;
        int childTop = t;

        for(int index=0; index < size; index++){
            Line line = mLines.get(index);
            int count = line.getChildCount();

            for(int i =0; i< count; i++){
                View child = line.getChildAt(i);
                if(null != child){
                    MarginLayoutParams layoutParams = (MarginLayoutParams)child.getLayoutParams();
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    int leftMargint = layoutParams.leftMargin;
                    int topMargin = layoutParams.topMargin;
                    int rightMargin = layoutParams.rightMargin;
                    int bottomMargin = layoutParams.bottomMargin;

                    child.layout(childLeft + leftMargint, childTop + topMargin, childLeft + leftMargint + childWidth, childTop + topMargin + childHeight);

                    childLeft +=(leftMargint + childWidth + rightMargin);

                    lineHeight = Math.max(lineHeight, topMargin + childHeight + bottomMargin);
                }
            }
            childLeft = l;
            childTop += lineHeight;

        }

    }

    static class Line{
        private List<View> children;

        public Line(){
            children = new ArrayList<>();
        }

        public void addChild(View view){
            if(!children.contains(view)) {
                children.add(view);
            }
        }

        public int getChildCount(){
            return children.size();
        }

        public View getChildAt(int index){
            if(index >= 0 && index < getChildCount()){
                return children.get(index);
            }
            return null;
        }
    }
}
