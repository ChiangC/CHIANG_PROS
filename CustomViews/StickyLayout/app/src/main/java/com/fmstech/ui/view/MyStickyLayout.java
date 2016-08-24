/*
 *  Copyright (C) 2015 ChiangCMBA
	https://git.oschina.net/chiangchunmba
	https://github.com/ChiangC
	
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.fmstech.ui.view;

import com.fmstech.ui.R;

import android.R.integer;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * @author Chiang.CMBA
 * @date Created on: 2015-3-12 上午10:45:11
 * @version v1.0
 * @Description: TODO(What's the class used for?) 
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyStickyLayout extends LinearLayout {
	
	private int mHeaderId;
	private int mContentId;
	
	private View mHeaderView;
	private View mContentView;
	
	private boolean mIntercepted = true;
	
	private static final int STATUS_EXPANDED = 0;
	private static final int STATUS_COLLAPSED = -1;
	
	private int mStatus = STATUS_EXPANDED;
	
	private int mLastInterceptMotionX;
	private int mLastInterceptMotionY;
	private int mLastMotionX;
	private int mLastMotionY;
	private int mFirstMotionX;
	private int mFirstMotionY;
	private int mHeaderCurrentHeight;
	private int mHeaderOriginHeight;
	private int mTouchSlop;
	
	private StatusDetectListener mStatusDetectListener;
	
	public MyStickyLayout(Context context) {
		this(context, null, 0);
		
	}
	
	public MyStickyLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}

	public MyStickyLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyLayout);
		mHeaderId = typedArray.getResourceId(R.styleable.StickyLayout_sticky_header, -1);
		if(mHeaderId == -1){
			throw new RuntimeException("The sticky_header must be set. It's required.");
		}
		mContentId = typedArray.getResourceId(R.styleable.StickyLayout_sticky_content, -1);
		if(mContentId == -1){
			throw new RuntimeException("The sticky_content must be set. It's required.");
		}
	}

	/**
	 * Finalize inflating a view from XML. This is called as the last phase of inflation, 
	 * after all child views have been added. Even if the subclass overrides onFinishInflate,
	 * they should always be sure to call the super method, so that we get called.
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
//		System.out.println("-------onFinishInflate()");
		//It is better to init views in onWindowFocusChanged();
//		if(mHeaderView == null || mContentView == null){
//			mHeaderView = findViewById(mHeaderId);
//			mContentView = findViewById(mContentId);
//		}
	}
	
	/**
	 * Called when the window containing this view gains or loses focus.
	 *  Note that this is separate from view focus: to receive key events,
	 *   both your view and its window must have focus. 
	 * If a window is displayed on top of yours that takes input focus, 
	 * then your own window will lose focus but the view focus will remain unchanged.
	 */
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
//		System.out.println("-------onWindowFocusChanged()");
		if(hasWindowFocus){
			initView();
		}
	}
	
	private void initView(){
		if(mHeaderView == null || mContentView == null){
			mHeaderView = findViewById(mHeaderId);
			mContentView = findViewById(mContentId);
			mHeaderOriginHeight = mHeaderView.getMeasuredHeight();
			mHeaderCurrentHeight = mHeaderOriginHeight;
			//Distance in pixels a touch can wander before we think the user is scrolling
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		}else{
			System.out.println("-------onWindowFocusChanged: views have been inited, it's not neccessary to reinit");
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
//		super.onInterceptTouchEvent(event);
        int currX = (int) event.getX();
        int currY = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mIntercepted = false;
			mFirstMotionX = mLastMotionX = mLastInterceptMotionX = currX;
			mFirstMotionY = mLastMotionY = mLastInterceptMotionY = currY;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = currX - mLastInterceptMotionX;
			int deltaY = currY - mLastInterceptMotionY;
			int dy = currY - mFirstMotionY;
			checkIntercept(deltaX , deltaY , dy);
			
			mLastInterceptMotionX = currX;
			mLastInterceptMotionY = currY;
//			System.out.println("-------mIntercepted: "+mIntercepted);
			break;
		case MotionEvent.ACTION_UP:
			mIntercepted = false;
			break;
		}
		
		return mIntercepted;
	}
	
	private void checkIntercept(int deltaX ,int deltaY , int dy){
		mIntercepted = false;
//		System.out.println("-------mStatus: "+mStatus);
//		System.out.println("-------mTouchSlop: "+mTouchSlop);
		int currentHeight = mHeaderCurrentHeight + deltaY;
		System.out.println("-------currentHeight: "+currentHeight);
		
		if(mStatus == STATUS_EXPANDED){
			System.out.println("-------mStatus == STATUS_EXPANDED");
			if((Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(dy) >= mTouchSlop)){
				mIntercepted = true;
			}
		}else{
			System.out.println("-------mStatus != STATUS_EXPANDED");
			if(detectStatus() && (Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(dy) >= mTouchSlop)){
				mIntercepted = true;
			}
		}
	}
	
	private int getHeaderCurrentHeight(){
		return mHeaderCurrentHeight;
	}
	
	private void resetHeaderHeight(int currentHeight){
		if(currentHeight <=0){
			currentHeight = 0;
		}else if(currentHeight > mHeaderOriginHeight){
			currentHeight = mHeaderOriginHeight;
		}
        if (currentHeight == 0) {
            mStatus = STATUS_COLLAPSED;
        } else {
            mStatus = STATUS_EXPANDED;
        }
//		System.out.println("-------mHeaderCurrentHeight: "+mHeaderCurrentHeight);
		if(mHeaderView != null && mHeaderView.getLayoutParams() != null){
			mHeaderView.getLayoutParams().height = currentHeight;
			mHeaderCurrentHeight = currentHeight;
			/**
			 * Call this when something has changed which has invalidated the layout of this view. 
			 * This will schedule a layout pass of the view tree. This should not be called while the
			 * view hierarchy is currently in a layout pass (isInLayout(). If layout is happening, 
			 * the request may be honored at the end of the current layout pass (and then layout will run again) 
			 * or after the current frame is drawn and the next layout occurs. 
			 */
			mHeaderView.requestLayout();
		}
        
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
        int currX = (int) event.getX();
        int currY = (int) event.getY();		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = currX;
			mLastMotionY = currY;
			break;
		case MotionEvent.ACTION_MOVE:
			if( (mStatus == STATUS_EXPANDED) || (mStatus == STATUS_COLLAPSED && detectStatus()) ){
				int deltaY = currY - mLastMotionY;
//				System.out.println("-------deltaY: "+deltaY);
				resetHeaderHeight(mHeaderCurrentHeight + deltaY);
				mLastMotionX = currX;
				mLastMotionY = currY;
			}
			break;
		case MotionEvent.ACTION_UP:
			int destHeight = 0;
			if(mHeaderCurrentHeight > mHeaderOriginHeight*0.5){
				destHeight = mHeaderOriginHeight;
				mStatus = STATUS_EXPANDED;
			}else{
				destHeight = 0;
				mStatus = STATUS_COLLAPSED;
			}
			doSmoothScrollAction(mHeaderCurrentHeight , destHeight , 500);
			break;
		}
		return true;
	}
	
	private void doSmoothScrollAction(final int from, final int to, int duration){
		final int frameCount = (int) ((duration/1000f)*30 + 1);
		final float partation = (to - from)/(float)frameCount;
		new Thread(){
			public void run(){
				for(int i=0; i < frameCount ; i++){
					final int currHeight;
					if(i == frameCount - 1){
						currHeight = to;
					}else{
						currHeight = (int) (from + partation*i); 
					}
					/*Causes the Runnable to be added to the message queue. 
					 * The runnable will be run on the user interface thread.
					 */
					post(new Runnable() {
						@Override
						public void run() {
							resetHeaderHeight(currHeight);
						}
					});
					
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void setStatusDetectListener(StatusDetectListener listener){
		mStatusDetectListener = listener;
	}
	
	/**
	 * 
	 * @return true if the headerView isExpandable, flase not.
	 */
	private boolean detectStatus(){
		if(mStatusDetectListener != null){
			return mStatusDetectListener.onDectectStatus();
		}
		//TODO
		return false;
	}
	
	public interface StatusDetectListener{
		public boolean onDectectStatus();
	}
}
