package com.teamhub.notiget.ui.base;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import java.util.Map;

public class BaseFragment extends Fragment {

    protected MutableLiveData<Map<String, Object>> liveMapData;

    public void setLiveMapData(MutableLiveData<Map<String, Object>> liveMapData) {
        this.liveMapData = liveMapData;
    }

    public MutableLiveData<Map<String, Object>> getLiveMapData() {
        if (liveMapData == null) {
            return null;
        }

        return liveMapData;
    }
}
