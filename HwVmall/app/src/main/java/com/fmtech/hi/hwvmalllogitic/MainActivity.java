package com.fmtech.hi.hwvmalllogitic;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<LogisticInfo> mLogisticInfos;
    private ListView mLogisticList;
    private View mNoneLogisticInfoView1;
    private View mNoneLogisticInfoView2;
    private LogisticInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int resId = getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null);
        if(resId > 0) {
            setTheme(resId);
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mLogisticList = (ListView)findViewById(R.id.lv_logistics_list);
        mNoneLogisticInfoView1 = findViewById(R.id.logistics_none_info);
        mNoneLogisticInfoView2 = findViewById(R.id.rl_logistics_none_info_layout);

        mLogisticInfos = new ArrayList<>();

        initData();

        mAdapter = new LogisticInfoAdapter(this, mLogisticInfos);
        mLogisticList.setAdapter(mAdapter);

        updateViews();

    }

    private void initData(){
        mLogisticInfos.add(0, new LogisticInfo("2016/06/22", "03:10:20", "快件离开【北京集散中心】，正发往【上海总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/22", "12:25:00", "快件到达【上海总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "10:10:20", "快件离开【上海集散中心】，正发往【广州总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "16:25:00", "快件到达【广州总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "14:40:20", "快件离开【广州总集散中心】，正发往【深圳总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "16:25:00", "快件到达【深圳总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "18:05:00", "快件离开【深圳总集散中心】，正发往【长沙集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "06:32:00", "快件到达【长沙集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "08:14:00", "快件离开【长沙集散中心】，正发往【永州】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "14:34:00", "快件到达【永州】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "15:29:00", "快件离开【永州】，正发往【永州东安】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "16:46:00", "快件到达【永州东安】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/25", "09:10:00", "快件正在派送途中，请您准备签收(派件人：xxx,电话：18877668888)"));
    }

    private void updateViews(){
        if(null == mLogisticInfos || mLogisticInfos.size() == 0){
            //show none logistic info views

        }else{
            //hide none logistic info views
            mNoneLogisticInfoView1.setVisibility(View.GONE);
            mNoneLogisticInfoView2.setVisibility(View.GONE);
//            mAdapter.notifyDataSetChanged();
            mLogisticList.setVisibility(View.VISIBLE);
        }
    }
/*
    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle paramBundle)
    {
        Logger.i("BaseActivity", "onCreate()");
        this.e = this;
        this.a = getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null);
        if (this.a == 0)
        {
            setTheme(2131230764);
            this.b = getActionBar();
            this.b.setLogo(new ColorDrawable(0));
            ((TextView)findViewById(Resources.getSystem().getIdentifier("action_bar_title", "id", "android"))).setTextColor(this.e.getResources().getColor(2131296256));
        }
        for (;;)
        {
            this.b.setDisplayHomeAsUpEnabled(true);
            this.b.show();
            super.onCreate(paramBundle);
            return;
            if (Constants.IS_EMUI_3EX1_UP)
            {
                Logger.i("BaseActivity", "isEmui4");
                setTheme(2131230771);
            }
            this.b = getActionBar();
            if (!Constants.IS_EMUI3_UP) {
                this.b.setBackgroundDrawable(this.e.getResources().getDrawable(2131296282));
            }
        }
    }*/
}
