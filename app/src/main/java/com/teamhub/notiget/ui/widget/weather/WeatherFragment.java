package com.teamhub.notiget.ui.widget.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class WeatherFragment extends Fragment {

    private WeatherViewModel viewModel;
    private View root;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        root = inflater.inflate(R.layout.widget_weather, container, false);

        // TODO: init reference

        return root;
    }
}