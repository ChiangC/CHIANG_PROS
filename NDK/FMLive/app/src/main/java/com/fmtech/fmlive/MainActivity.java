package com.fmtech.fmlive;

import com.fmtech.fmlive.jni.PushNative;
import com.fmtech.fmlive.listener.LiveStateChangeListener;
import com.fmtech.fmlive.pusher.LivePusher;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements LiveStateChangeListener{

	private static final String URL = "rtmp://106.14.33.215:1935/live/fmlive";
	private static final int REQUEST_CAMERA_AUDIO_PERMISSION_CODE = 100;
	private LivePusher mLivePusher;
	private boolean isPushing;
	private Button mPushBtn;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PushNative.CONNECT_FAILED:
				Toast.makeText(MainActivity.this, "Connect Failed",Toast.LENGTH_SHORT).show();
				break;
			case PushNative.INIT_FAILED:
				Toast.makeText(MainActivity.this, "Init failed",Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPushBtn = (Button)findViewById(R.id.btn_push);
		
		SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		mLivePusher = new LivePusher(surfaceHolder);
	}
	
	public void startLive(View view){
		if (Build.VERSION.SDK_INT>22){
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
               
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, REQUEST_CAMERA_AUDIO_PERMISSION_CODE);

            }else {
            	startLive();
            }
        }else {
        	startLive();
        }
		
	}
	
	private void startLive(){
		if(!isPushing){
			isPushing = true;
			mLivePusher.startPush(URL, this);
			mPushBtn.setText(getString(R.string.stop_pushing));
		}else{
			isPushing = false;
			mLivePusher.stopPush();
			mPushBtn.setText(getString(R.string.start_pushing));
		}
	}
	
	public void switchCamera(View view){
		mLivePusher.switchCamera();
	}

	@Override
	public void onError(int code) {
		mHandler.sendEmptyMessage(code);
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode == REQUEST_CAMERA_AUDIO_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
			int size = grantResults.length;
			boolean granted = true;
			for(int i=0;i < size; i++){
				if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
					granted = false;
				}
			}
			if(granted) {
				startLive();
			}
		}
	}
}
