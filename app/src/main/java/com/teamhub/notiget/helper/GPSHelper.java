package com.teamhub.notiget.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

public class GPSHelper {

    public static final int PERMISSION_REQUEST_CODE = 1000;

    private final Activity context;
    private final LocationManager locationManager;

    public String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    public interface GPSToolListener {
        void onGPSLocationUpdated(Location location);
        void onNetworkLocationUpdated(Location location);
    }

    public GPSHelper(Activity context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public GPSHelper(Activity context, GPSToolListener listener) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            getPermissions(permissions);
        }

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

    public Location getLastLocation() {
        Location location = null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissions(permissions);
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        return location;
    }

    private void getPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(context, permissions, PERMISSION_REQUEST_CODE);
    }
}
