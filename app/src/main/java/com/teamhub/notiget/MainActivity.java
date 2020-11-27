package com.teamhub.notiget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.teamhub.notiget.helper.GPSHelper;
import com.teamhub.notiget.ui.base.BaseFragment;
import com.teamhub.notiget.ui.main.MainFragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private GPSHelper gpsHelper;
    private Geocoder geocoder;
    private MutableLiveData<Map<String, Object>> liveMapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        geocoder = new Geocoder(this, Locale.KOREA);
        liveMapData = new MutableLiveData<>(new HashMap<>());

        if (GPSHelper.checkPermissions(this, GPSHelper.permissions)) {
            initGpsHelper();
        } else {
            GPSHelper.getPermissions(this, GPSHelper.permissions);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), "MainFragment")
                    .commitNow();

            ((BaseFragment) getSupportFragmentManager().findFragmentByTag("MainFragment"))
                    .setLiveMapData(liveMapData);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GPSHelper.PERMISSION_REQUEST_CODE
                && GPSHelper.checkPermissions(this, permissions)) {
            initGpsHelper();
        } else {
            Toast.makeText(this, R.string.ui_permission_denied, Toast.LENGTH_LONG).show();
        }
    }

    private void initGpsHelper() {
        gpsHelper = new GPSHelper(this, new GPSHelper.GPSToolListener() {

            @Override
            public void onGPSLocationUpdated(Location location) {
                Map<String, Object> map = liveMapData.getValue();
                map.put("gpsLocation", location);
                try {
                    map.put("gpsAddress",
                            geocoder.getFromLocation(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    1).get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                liveMapData.postValue(map);
            }

            @Override
            public void onNetworkLocationUpdated(Location location) {
                Map<String, Object> map = liveMapData.getValue();
                map.put("networkLocation", location);
                try {
                    map.put("networkAddress",
                            geocoder.getFromLocation(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    1).get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                liveMapData.postValue(map);
            }

        });
    }
}