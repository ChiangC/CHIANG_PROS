package com.fmtech.hi.hwvmalllogitic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2016 Mtelnet All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/6/26 17:23
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class LogisticInfoAdapter extends BaseAdapter {

    private Context mContext;
    private List<LogisticInfo> mLogisticInfos;

    public LogisticInfoAdapter(Context context, List<LogisticInfo> logisticInfos){
        mContext = context;
        mLogisticInfos = logisticInfos;
    }

    @Override
    public int getCount() {
        return mLogisticInfos == null?0:mLogisticInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mLogisticInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_logistic_current, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(0 != position){
            int color = mContext.getResources().getColor(R.color.gray_text);
            viewHolder.logisticCurrDate.setTextColor(color);
            viewHolder.logisticCurrTime.setTextColor(color);
            viewHolder.logisticDetail.setTextColor(mContext.getResources().getColor(R.color.logistics_text_black_color));
            viewHolder.point.setBackgroundResource(R.mipmap.logistic_before_point);
        }else{
            int color = mContext.getResources().getColor(R.color.logistics_item_current_time_text_color);
            viewHolder.logisticCurrDate.setTextColor(color);
            viewHolder.logisticCurrTime.setTextColor(color);
            viewHolder.logisticDetail.setTextColor(color);
            viewHolder.point.setBackgroundResource(R.mipmap.logistic_current_point);
        }

        viewHolder.logisticCurrDate.setText(mLogisticInfos.get(position).getLogisticDate());
        viewHolder.logisticCurrTime.setText(mLogisticInfos.get(position).getLogisticTime());
        viewHolder.logisticDetail.setText(mLogisticInfos.get(position).getLogisticDetail());
        return convertView;
    }

    class ViewHolder{
        public TextView logisticCurrDate;
        public TextView logisticCurrTime;
        public TextView logisticDetail;
        public ImageView point;

        public ViewHolder(View view){
            logisticCurrDate = (TextView)view.findViewById(R.id.tv_logistic_item_time_date);
            logisticCurrTime = (TextView)view.findViewById(R.id.tv_logistic_item_time_detail);
            logisticDetail = (TextView)view.findViewById(R.id.tv_logistic_item_content);
            point = (ImageView) view.findViewById(R.id.iv_logistic_item_point);
        }
    }
}