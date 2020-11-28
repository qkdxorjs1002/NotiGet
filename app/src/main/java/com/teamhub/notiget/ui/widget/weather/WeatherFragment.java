package com.teamhub.notiget.ui.widget.weather;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.teamhub.notiget.R;
import com.teamhub.notiget.formatter.XAxisLabelFormatter;
import com.teamhub.notiget.ui.widget.base.BaseWidgetFragment;

public class WeatherFragment extends BaseWidgetFragment {

    private WeatherViewModel viewModel;
    private View root;

    private ImageView weatherNowIcon;
    private TextView weatherNowTemp;
    private TextView weatherNowFeelsLike;
    private TextView weatherNowHumidity;
    private TextView weatherNowDust;
    private TextView weatherNowPM10;
    private TextView weatherNowPM2_5;
    private LineChart weatherHourlyTempGraph;

    public static WeatherFragment newInstance(View v) {
        return new WeatherFragment(v);
    }

    public WeatherFragment(View v) {
        super(v);
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

    private void initReferences() {
        weatherNowIcon = (ImageView) root.findViewById(R.id.WeatherNowIcon);
        weatherNowTemp = (TextView) root.findViewById(R.id.WeatherNowTemp);
        weatherNowFeelsLike = (TextView) root.findViewById(R.id.WeatherNowFeelsLike);
        weatherNowHumidity = (TextView) root.findViewById(R.id.WeatherNowHumidity);
        weatherNowDust = (TextView) root.findViewById(R.id.WeatherNowDust);
        weatherNowPM10 = (TextView) root.findViewById(R.id.WeatherNowPM10);
        weatherNowPM2_5 = (TextView) root.findViewById(R.id.WeatherNowPM2_5);
        weatherHourlyTempGraph = (LineChart) root.findViewById(R.id.WeatherHourlyTempGraph);

        weatherHourlyTempGraph.setTouchEnabled(false);
        weatherHourlyTempGraph.setDragEnabled(false);
        weatherHourlyTempGraph.setScaleEnabled(false);
        weatherHourlyTempGraph.setPinchZoom(false);
        weatherHourlyTempGraph.setDrawGridBackground(false);
        weatherHourlyTempGraph.setMaxHighlightDistance(300);
        weatherHourlyTempGraph.getXAxis().setLabelCount(7);
        weatherHourlyTempGraph.getXAxis().setAxisLineColor(Color.BLACK);
        weatherHourlyTempGraph.getXAxis().setValueFormatter(new XAxisLabelFormatter());
        weatherHourlyTempGraph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        weatherHourlyTempGraph.getAxisLeft().setEnabled(false);
        weatherHourlyTempGraph.getAxisRight().setEnabled(false);
    }

    private void initObservers() {
        liveMapData.observe(getViewLifecycleOwner(), stringObjectMap -> {
            viewModel.setLocationData(stringObjectMap);
        });

        viewModel.location.observe(getViewLifecycleOwner(), location -> {
            viewModel.getWeather(location.getLatitude(), location.getLongitude());
        });

        viewModel.address.observe(getViewLifecycleOwner(), address -> {
            viewModel.getDescription(address).observe(getViewLifecycleOwner(), s -> {
                weatherHourlyTempGraph.getDescription().setText(s);
            });
            viewModel.getDust(address);
        });

        viewModel.weatherData.observe(getViewLifecycleOwner(), oneCallModel -> {
            Glide.with(root)
                    .load("https://openweathermap.org/img/wn/"
                            .concat(oneCallModel.current.weather.get(0).icon)
                            .concat("@4x.png"))
                    .override(Target.SIZE_ORIGINAL)
                    .into(weatherNowIcon);

            weatherNowIcon.setContentDescription(oneCallModel.current.weather.get(0).description);

            weatherNowTemp.setText(viewModel.kelvinToCelsius(oneCallModel.current.temp));
            weatherNowFeelsLike.setText("체감 "
                    .concat(viewModel.kelvinToCelsius(oneCallModel.current.feels_like)));
            weatherNowHumidity.setText("습도 "
                    .concat(String.valueOf(oneCallModel.current.humidity).concat("%")));

            viewModel.makeHourlyChart(oneCallModel.hourly)
                    .observe(getViewLifecycleOwner(), lineData -> {
                        weatherHourlyTempGraph.setData(lineData);
                        weatherHourlyTempGraph.invalidate();
            });
        });

        viewModel.dustData.observe(getViewLifecycleOwner(), dustModel -> {
            viewModel.gradeToText(dustModel.pm10Value).observe(getViewLifecycleOwner(), s -> {
                weatherNowDust.setText(s);
            });

            weatherNowPM10.setText(String.valueOf(dustModel.pm10Value));
            weatherNowPM2_5.setText(String.valueOf(dustModel.pm25Value));
        });
    }

    private void initEvents() {
        root.setOnClickListener(v -> {
            viewModel.setLocationData(liveMapData.getValue());
        });
    }
}