package com.fmtech.fmweather;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.fmtech.fmweather.domain.Weather;
import com.fmtech.fmweather.ui.activity.BaseActivity;

import rx.Observer;

public class MainActivity extends BaseActivity {

    private CollapsingToolbarLayout mCollaspingToolbarLayout;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mFloatingActionBtn;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mBanner;
    private ProgressBar mProgressBar;
    private ImageView mErrorImageView;
    private RelativeLayout mHeaderBackground;
    private RecyclerView mRecyclerView;
//    private Weather mWeather = new Weather();
//    private WeatherAdapter mWeatherAdapter;
    private Observer<Weather> mObserver;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
