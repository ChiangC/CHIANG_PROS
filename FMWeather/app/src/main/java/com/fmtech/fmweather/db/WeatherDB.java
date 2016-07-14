package com.fmtech.fmweather.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fmtech.fmweather.domain.City;
import com.fmtech.fmweather.domain.Province;
import com.fmtech.fmweather.util.Utils;

import java.util.ArrayList;
import java.util.List;

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

public class WeatherDB {

    public WeatherDB() {

    }

    public static List<Province> queryProvinces(SQLiteDatabase db) {
        if (null == db) {
            return null;
        }

        List<Province> mProvinces = new ArrayList<>();
        Cursor cursor = db.query("T_Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.ProSort = cursor.getInt(cursor.getColumnIndex("ProSort"));
                province.ProName = cursor.getString(cursor.getColumnIndex("ProName"));
                mProvinces.add(province);
            } while (cursor.moveToNext());
        }
        Utils.closeQuietly(cursor);

        return mProvinces;
    }

    public static List<City> loadCities(SQLiteDatabase db, int ProID) {
        if (null == db) {
            return null;
        }

        List<City> cities = new ArrayList<>();
        Cursor cursor = db.query("T_City", null, "ProID = ?", new String[]{String.valueOf(ProID)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.CityName = cursor.getString(cursor.getColumnIndex("CityName"));
                city.ProID = ProID;
                city.CitySort = cursor.getInt(cursor.getColumnIndex("CitySort"));
                cities.add(city);
            } while (cursor.moveToNext());
        }
        Utils.closeQuietly(cursor);
        return cities;
    }
}
