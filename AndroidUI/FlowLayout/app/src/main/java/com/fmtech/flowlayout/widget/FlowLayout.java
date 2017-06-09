package com.fmtech.flowlayout.widget;

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

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;
        int childHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;

        int widthNoPadding = widthSize - getPaddingLeft() - getPaddingRight();
        int heightNoPadding = heightSize - getPaddingTop() - getPaddingBottom();

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthNoPadding, childWidthMode);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightNoPadding, childHeightMode);
        
        mLines = new ArrayList<>();

        int childCount = getChildCount();

        int widthUsed = 0;
        Line currentLine = new Line();

        int i = 0;
        for(i = 0; i < childCount; i++){
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            measureChild(child, childWidthMeasureSpec, childHeightMeasureSpec);
            int childWidthAndHorizontalMargin = (layoutParams.leftMargin + layoutParams.rightMargin + child.getMeasuredWidth());

            if((widthUsed + childWidthAndHorizontalMargin) > widthNoPadding){
                mLines.add(currentLine);
                // new line
                currentLine = new Line();
                currentLine.addChild(child);

                widthUsed = 0;
                widthUsed += childWidthAndHorizontalMargin;

            }else{
                currentLine.addChild(child);
                widthUsed += childWidthAndHorizontalMargin;
            }
        }

        if(i == childCount && currentLine.getChildCount() > 0 && !mLines.contains(currentLine)){
            mLines.add(currentLine);
        }

        int totalHeight = 0;
        for (Line line : mLines) {
            totalHeight += line.getLineHeight();
        }
        totalHeight += getPaddingTop() + getPaddingBottom();

//        setMeasuredDimension(widthSize, totalHeight);
        setMeasuredDimension(widthSize, resolveSize(totalHeight, heightMeasureSpec));
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
            lineHeight = 0;

        }

    }

    static class Line{
        private List<View> children;
        int lineHeight = 0;
        int lineWidth = 0;

        public Line(){
            children = new ArrayList<>();
        }

        public void addChild(View view){
            if(!children.contains(view)) {
                children.add(view);
                MarginLayoutParams layoutParams = (MarginLayoutParams)view.getLayoutParams();
                lineHeight = Math.max(lineHeight, (view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin));
                lineWidth += view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
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

        public int getLineHeight() {
            return lineHeight;
        }

        public void setLineHeight(int lineHeight) {
            this.lineHeight = lineHeight;
        }

        public int getLineWidth() {
            return lineWidth;
        }

        public void setLineWidth(int lineWidth) {
            this.lineWidth = lineWidth;
        }
    }
}
