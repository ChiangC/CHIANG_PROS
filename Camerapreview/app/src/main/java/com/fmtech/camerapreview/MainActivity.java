package com.fmtech.camerapreview;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    /**
     * Called when the activity is first created.
     */
    private static final String TAG = "yan:";
    SurfaceView mySurfaceView = null;
    SurfaceHolder mySurfaceHolder = null;
    Button btnPreview = null;
    Button btnPhoto = null;
    Button btnSave = null;
    Camera myCamera = null;
    Camera.Parameters myParameters;
    boolean isView = false;
    Bitmap bm;
    String savePath = "/mnt/sdcard/testPhoto/";
    int cntSave = 0;
    private Camera.AutoFocusCallback mAutoFocusCallback;
    //private Camera.PreviewCallback mPreviewCallback;
    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //璁剧疆鍏ㄥ睆鏃犳爣棰?       requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window myWindow = this.getWindow();
        myWindow.setFlags(flag, flag);
        setContentView(R.layout.activity_main); //璁剧疆甯冨眬
        mySurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);
        mySurfaceView.setZOrderOnTop(true);
        mySurfaceHolder = mySurfaceView.getHolder();
        mySurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        btnPreview = (Button) findViewById(R.id.btnPreview);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        btnSave = (Button) findViewById(R.id.btnSave);
        if (!isFolderExist(savePath)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("閿欒");
            alertDialog.setMessage("鍥剧墖淇濆瓨鏂囦欢澶瑰垱寤哄け璐ワ紒");
            alertDialog.setPositiveButton("纭畾", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MainActivity.this.finish();
                }
            });
            alertDialog.show();
        } else
            Toast.makeText(MainActivity.this,
                    "鎮ㄧ殑鐓х墖灏嗕繚瀛樺湪锛?" + savePath,
                    Toast.LENGTH_SHORT).show();
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mAutoFocusCallback = new Camera.AutoFocusCallback() {

            public void onAutoFocus(boolean success, Camera camera) {
                // TODO Auto-generated method stub
                if (success) {
                    myCamera.setOneShotPreviewCallback(null);
                    Toast.makeText(MainActivity.this,
                            "鑷姩鑱氱劍鎴愬姛锛?",
                            Toast.LENGTH_SHORT).show();
                }

            }
        };
        mTimer = new Timer();
        mTimerTask = new CameraTimerTask();
        //mTimer.schedule(mTimerTask, 0, 500);

        btnPreview.setOnClickListener(new BtnListener());
        btnPhoto.setOnClickListener(new BtnListener());
        btnSave.setOnClickListener(new BtnListener());

    }

    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback() {

        public void onShutter() {
            // TODO Auto-generated method stub

        }
    };
    Camera.PictureCallback myRawCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub

        }
    };
    Camera.PictureCallback myjpegCalback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.i(TAG, "onPictureTaken........");
            bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            isView = false;
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
            isView = false;

        }
    };

    class BtnListener implements View.OnClickListener {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.btnPreview:
                    Toast.makeText(MainActivity.this,
                            "鎮ㄦ寜浜嗛瑙堟寜閽?",
                            Toast.LENGTH_SHORT).show();
                    initCamera();
                    break;
                case R.id.btnPhoto:
                    if (isView && myCamera != null) {

                        myCamera.takePicture(myShutterCallback, myRawCallback, myjpegCalback);
                    } else
                        Toast.makeText(MainActivity.this, "璇峰厛棰勮鐒跺悗鎷嶇収锛?", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnSave:
                    if (bm == null) {
                        Toast.makeText(MainActivity.this, "璇锋媿鎽勬垚鍔熷悗鍐嶄繚瀛橈紒锛侊紒", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int b = 0, c = 1;
                    File fTest = new File(savePath + b + c + ".JPG");
                    while (fTest.exists()) {
                        if (c == 9) {
                            b++;
                            c = 0;
                        } else
                            c++;
                        if (b == 9) {
                            b = 0;
                            Toast.makeText(MainActivity.this, "姝ゅ織鎰胯€呮牱鏈暟鐩凡瓒呰繃100锛?",
                                    Toast.LENGTH_SHORT).show();
                        }
                        fTest = new File(savePath + b + c + ".JPG");
                    }
                    try {
                        FileOutputStream fout = new FileOutputStream(fTest);
                        BufferedOutputStream bos = new BufferedOutputStream(fout);
                        Bitmap mBitmap = Bitmap.createScaledBitmap(bm, 600, 800, false);
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        //bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        bos.flush();
                        bos.close();
                        cntSave++;
                        Toast.makeText(MainActivity.this, "鎮ㄦ媿鐨勭" + cntSave + "寮爌icture淇濆瓨鎴愬姛锛佺紪鍙凤細" + b + c,
                                Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "淇濆瓨澶辫触",
                                Toast.LENGTH_SHORT).show();
                    }


                    break;
                default:

            }
        }
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub


    }

    //鍒濆鍖栨憚鍍忓ご
    public void initCamera() {
        if (myCamera == null && !isView) {
            myCamera = Camera.open();
            Log.i(TAG, "camera.open");
        }
        if (myCamera != null && !isView) {
            try {

                myParameters = myCamera.getParameters();
                myParameters.setPictureFormat(PixelFormat.JPEG);
                myParameters.setPreviewSize(1280, 720);
                //myParameters.setFocusMode("auto");

                myParameters.setPictureSize(2048, 1152); //1280, 720

                myParameters.set("rotation", 90);
                myCamera.setDisplayOrientation(90);
//                myCamera.setParameters(myParameters);
                myCamera.setPreviewDisplay(mySurfaceHolder);
                myCamera.startPreview();
                isView = true;
                myCamera.autoFocus(mAutoFocusCallback);

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "鍒濆鍖栫浉鏈洪敊璇?",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    class CameraTimerTask extends TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (myCamera != null) {
                myCamera.autoFocus(mAutoFocusCallback);
            }

        }

    }

    public boolean isFolderExist(String folderPath) {
        boolean result = false;
        File f = new File(folderPath);
        if (!f.exists())

        {
            if (f.mkdir()) {
                result = true;
            } else
                result = false;
        } else
            result = true;

        return result;
    }
}
