package com.teamhub.notiget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.teamhub.notiget.helper.GPSHelper;
import com.teamhub.notiget.ui.base.BaseFragment;
import com.teamhub.notiget.ui.main.MainFragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), "MainFragment")
                    .commitNow();

            ((BaseFragment) getSupportFragmentManager().findFragmentByTag("MainFragment"))
                    .setLiveMapData(liveMapData);
        }
    }
}