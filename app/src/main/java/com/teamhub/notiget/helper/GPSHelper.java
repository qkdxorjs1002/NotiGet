package com.teamhub.notiget.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

public class GPSHelper {

    public static final int PERMISSION_REQUEST_CODE = 1000;
    public static final String[] permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private final Activity activity;
    private final LocationManager locationManager;


    public interface GPSToolListener {
        void onGPSLocationUpdated(Location location);
        void onNetworkLocationUpdated(Location location);
    }

    public GPSHelper(Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    public GPSHelper(Activity activity, GPSToolListener listener) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (checkPermissions(activity, permissions)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000,
                    10,
                    listener::onGPSLocationUpdated
            );

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10000,
                    10,
                    listener::onNetworkLocationUpdated
            );
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLastLocation() {
        Location location = null;

        if (checkPermissions(activity, permissions)) {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        return location;
    }

    public static void getPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    public static boolean checkPermissions(Activity activity, String[] perms) {
        boolean isGranted = true;

        for (String perm : perms) {
            if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
        }

        return isGranted;
    }
}
