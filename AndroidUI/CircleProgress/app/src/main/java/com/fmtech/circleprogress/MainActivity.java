package com.fmtech.circleprogress;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fmtech.circleprogress.view.CircleProgressBar;

public class MainActivity extends AppCompatActivity {

    private CircleProgressBar mCircleProgressBar;
    private Handler mHandler = new Handler();
    private int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleProgressBar = (CircleProgressBar)findViewById(R.id.circle_progressbar);

        mCircleProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress = 0;
                        while(mProgress <= 100){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mCircleProgressBar.setProgress(mProgress);
                                }
                            });
                            mProgress += 2;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
