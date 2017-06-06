package com.fmtech.quickindexbar;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fmtech.quickindexbar.adapter.PersonAdapter;
import com.fmtech.quickindexbar.domain.Person;
import com.fmtech.quickindexbar.utils.Cheeses;
import com.fmtech.quickindexbar.view.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity implements QuickIndexBar.OnLetterSelectedListener{

    private ListView mListView;
    private TextView mLetterInfo;
    private QuickIndexBar mQuickIndexBar;
    private Handler mHandler = new Handler();

    private ArrayList<Person> mPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView)findViewById(R.id.lv_persons);
        mLetterInfo = (TextView)findViewById(R.id.tv_letter_info);
        mQuickIndexBar = (QuickIndexBar)findViewById(R.id.quick_index_bar);

        mQuickIndexBar.setOnLetterSelectedListener(this);

        mPersons = new ArrayList<Person>();
        fillAndSortData(mPersons);

        mListView.setAdapter(new PersonAdapter(MainActivity.this, mPersons));

    }

    @Override
    public void onSelected(String letter) {
        showLetterInfo(letter);
        for(int i=0; i<mPersons.size(); i++){
            if(TextUtils.equals(letter, mPersons.get(i).getPinyin().charAt(0)+"")){
                mListView.setSelection(i);
                break;
            }
        }
    }

    private void showLetterInfo(String letter){
        mLetterInfo.setText(letter);
        mLetterInfo.setVisibility(View.VISIBLE);

        mHandler.removeCallbacks(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLetterInfo.setVisibility(View.GONE);
            }
        }, 1200);
    }

    private void fillAndSortData(ArrayList<Person> persons) {
        // 填充数据
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            String name = Cheeses.NAMES[i];
            persons.add(new Person(name));
        }

        // 进行排序
        Collections.sort(persons);
    }
}
