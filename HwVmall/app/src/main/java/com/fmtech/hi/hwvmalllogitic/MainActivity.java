package com.fmtech.hi.hwvmalllogitic;

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
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "14:40:20", "快件离开【广州总集散中心】，正发往【深圳总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "16:25:00", "快件到达【深圳总集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/23", "18:05:00", "快件离开【深圳总集散中心】，正发往【长沙集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "06:32:00", "快件到达【长沙集散中心】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "08:14:00", "快件离开【长沙集散中心】，正发往【永州】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "14:34:00", "快件到达【永州】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "15:29:00", "快件离开【永州】，正发往【永州东安】"));
        mLogisticInfos.add(0, new LogisticInfo("2016/06/24", "16:46:00", "快件到达【永州东安】"));
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

}
