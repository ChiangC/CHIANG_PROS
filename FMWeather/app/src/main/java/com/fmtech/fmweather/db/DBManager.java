package com.fmtech.fmweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.fmtech.fmweather.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/7/14 21:54
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class DBManager {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "china_city_db";
    public static final String PACKAGE_NAME = "com.fmtech.fmweather";
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DBManager(Context context){
        mContext = context;
    }

    public SQLiteDatabase getDatabase(){
        return mDatabase;
    }

    public void openDatabase(){
        mDatabase = openDatabase(DB_PATH + "/" + DB_NAME);
    }

    private SQLiteDatabase openDatabase(String dbFilePath){
        try {
            if(!(new File(dbFilePath)).exists()){
                InputStream inputStream = mContext.getResources().openRawResource(R.raw.china_city);
                FileOutputStream fos = new FileOutputStream(dbFilePath);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = inputStream.read(buffer)) > 0){
                    fos.write(buffer, 0, count);
                }
                fos.close();
                inputStream.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbFilePath, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public void closeDatabase(){
        mDatabase.close();
    }
}
