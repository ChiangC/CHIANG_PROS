package com.fmtech.quickindexbar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fmtech.quickindexbar.R;
import com.fmtech.quickindexbar.domain.Person;

import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/6/15 22:35
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/6/15 22:35  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class PersonAdapter extends BaseAdapter {

    private Context mContext;
    private List<Person> mPersons;

    public PersonAdapter(Context context, List<Person> persons){
        mContext = context;
        mPersons = persons;
    }

    @Override
    public int getCount() {
        return mPersons == null ? 0:mPersons.size();
    }

    @Override
    public Object getItem(int position) {
        return mPersons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(null == convertView){
            convertView = View.inflate(mContext, R.layout.list_item, null);
        }
        ViewHolder viewHolder = ViewHolder.getHolder(convertView);
        Person person = mPersons.get(position);
        String str = null;
        String curretnLetter = person.getPinyin().charAt(0)+"";
        if(position == 0){
            str = curretnLetter;
        }else{
            String preLetter = mPersons.get(position -1).getPinyin().charAt(0)+"";
            if(!TextUtils.equals(curretnLetter, preLetter)){
                str = curretnLetter;
            }
        }

        viewHolder.tvPingyinIndex.setText(person.getPinyin().charAt(0)+"");
        viewHolder.tvPingyinIndex.setVisibility(str == null ? View.GONE:View.VISIBLE);
        viewHolder.tvName.setText(person.getName());

        return convertView;
    }


    static class ViewHolder{
        public TextView tvPingyinIndex;
        public TextView tvName;

        static ViewHolder getHolder(View convertView){
            Object tag = convertView.getTag();
            if(null == tag){
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
                viewHolder.tvPingyinIndex = (TextView)convertView.findViewById(R.id.tv_index);
                convertView.setTag(viewHolder);
                return viewHolder;
            }else{
                return (ViewHolder)tag;
            }
        }
    }
}