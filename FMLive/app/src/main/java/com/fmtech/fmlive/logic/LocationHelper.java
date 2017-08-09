package com.fmtech.fmlive.logic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.fmtech.fmlive.R;
import com.fmtech.fmlive.util.Constants;

import java.io.IOException;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2017 FMTech All Rights Reserved.
 *
 * Created by Drew.Chiang on 17/8/9 17:27
 *
 * @version v1.0.0
 *
 * ==================================================================
 */


public class LocationHelper {
    private static final String TAG = LocationHelper.class.getSimpleName();

    private static LocationListener sLocationListener;

    public static boolean checkLocationPermission(final @NonNull Activity activity){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            if(PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
               ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_PERMISSION_REQ_CODE);
                return false;
            }
        }

        return true;
    }

    private static String getAddressFromLocation(final @NonNull Activity activity, Location location){
        Geocoder geocoder = new Geocoder(activity);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

            if(null != addressList && addressList.size() > 0){
                Address address = addressList.get(0);
                String sAddress = "";

                if(!TextUtils.isEmpty(address.getLocality())){
                    if(!TextUtils.isEmpty(address.getFeatureName())){
                        sAddress = address.getLocality() + " " + address.getFeatureName();
                    }else{
                        sAddress = address.getLocality();
                    }
                }else{
                    sAddress = "Locating failed";
                }
                return sAddress;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static boolean getMyLoaction(final  @NonNull Activity activity, final @NonNull OnLocationListener listener){
        LocationManager locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(activity.getString(R.string.location_service_not_available));
            builder.setPositiveButton(R.string.enable_location_service, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivity(myIntent);
                }
            });
            builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
            return false;
        }

        if(!checkLocationPermission(activity)){
            return true;
        }

        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(null == currentLocation){
            sLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    String strAddr = getAddressFromLocation(activity, location);
                    if (TextUtils.isEmpty(strAddr)) {
                        listener.onLocationChanged(-1, 0, 0, strAddr);
                    } else {
                        listener.onLocationChanged(0, location.getLatitude(), location.getLongitude(), strAddr);
                    }
                    locationManager.removeUpdates(this);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    locationManager.removeUpdates(this);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    locationManager.removeUpdates(this);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    locationManager.removeUpdates(this);
                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 8000, 0, sLocationListener);
        }else{
            String addrStr = getAddressFromLocation(activity, currentLocation);
            if(TextUtils.isEmpty(addrStr)){
                listener.onLocationChanged(-1, 0, 0, addrStr);
            }else{
                listener.onLocationChanged(0, currentLocation.getLatitude(), currentLocation.getLongitude(), addrStr);
            }
        }
        return true;
    }


    public interface OnLocationListener {
        void onLocationChanged(int code, double lat1, double long1, String location);
    }

}
