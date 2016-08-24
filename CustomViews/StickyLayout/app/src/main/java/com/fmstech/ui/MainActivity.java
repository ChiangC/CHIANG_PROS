package com.fmstech.ui;

import com.fmstech.ui.view.MyStickyLayout;
import com.fmstech.ui.view.MyStickyLayout.StatusDetectListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements StatusDetectListener{

	private MyStickyLayout mStickyLayout;
	private ScrollView mScrollView;
	
	private boolean mExpandable = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStickyLayout = (MyStickyLayout)findViewById(R.id.sticky_layout);
		mScrollView = (ScrollView)findViewById(R.id.scrollview);
		mStickyLayout.setStatusDetectListener(this);
	}
	
	
	public void showMenuItemInfo(View v){
		TextView tv = (TextView) v;
		Toast.makeText(this,tv.getText(), 0).show();
		mExpandable = !mExpandable;
	}


	@Override
	public boolean onDectectStatus() {
		// TODO Auto-generated method stub
		return mExpandable;
	}
}
