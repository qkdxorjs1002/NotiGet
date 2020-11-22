package com.teamhub.notiget.ui.widget.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.teamhub.notiget.R;

public class WeatherFragment extends Fragment {

    private WeatherViewModel viewModel;
    private View root;

    private ImageView weatherCurrentIcon;
    private TextView weatherCurrentTemp;
    private TextView weatherCurrentState;
    private TextView weatherCurrentDescription;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        root = inflater.inflate(R.layout.widget_weather, container, false);

        initReferences();
        initObservers();
        initEvents();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initReferences() {
        weatherCurrentIcon = (ImageView) root.findViewById(R.id.WeatherCurrentIcon);
        weatherCurrentTemp = (TextView) root.findViewById(R.id.WeatherCurrentTemp);
        weatherCurrentState = (TextView) root.findViewById(R.id.WeatherCurrentState);
        weatherCurrentDescription = (TextView) root.findViewById(R.id.WeatherCurrentDescription);
        viewModel.getWeather(37.476543, 127.048116);
    }

    private void initObservers() {
        viewModel.weatherData.observe(getViewLifecycleOwner(), oneCallModel -> {
            Glide.with(root)
                    .load("https://openweathermap.org/img/wn/"
                            .concat(oneCallModel.current.weather.get(0).icon)
                            .concat("@4x.png"))
                    .override(Target.SIZE_ORIGINAL)
                    .into(weatherCurrentIcon);

            weatherCurrentTemp.setText(String.valueOf(Math.round(273.15 - oneCallModel.current.temp)).concat("°C"));
            weatherCurrentState.setText(oneCallModel.current.weather.get(0).description);
            weatherCurrentDescription.setText("체감 ".concat(String.valueOf(Math.round(273.15 - oneCallModel.current.feels_like)).concat("°C")));
        });
    }

    private void initEvents() {

    }
}