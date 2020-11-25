package com.teamhub.notiget.ui.widget.weather;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.teamhub.notiget.adapter.DataLabelFormatter;
import com.teamhub.notiget.model.weather.dust.DustModel;
import com.teamhub.notiget.model.weather.openweather.HourlyModel;
import com.teamhub.notiget.model.weather.openweather.OneCallModel;
import com.teamhub.notiget.repository.weather.dust.DustRepository;
import com.teamhub.notiget.repository.weather.openweather.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;
    private DustRepository dustRepository;
    public MutableLiveData<OneCallModel> weatherData;
    public MutableLiveData<DustModel> dustData;

    public WeatherViewModel() {
        weatherRepository = new WeatherRepository();
        dustRepository = new DustRepository();
        weatherData = new MutableLiveData<>();
        dustData = new MutableLiveData<>();
    }

    public void getWeather(double lat, double lon) {
        weatherRepository.getWeather(lat, lon).observeForever(oneCallModel -> {
            weatherData.setValue(oneCallModel);
        });
    }

    public void getDust(String city) {
        dustRepository.getDust(city.toLowerCase()).observeForever(dustModel -> {
            dustData.setValue(dustModel);
        });
    }

    public LiveData<String> gradeToText(String grade) {
        MutableLiveData<String> gradeText = new MutableLiveData<>();

        String pmGradeText = "관측없음";

        switch (grade) {
            case "1":
                pmGradeText = "좋음";
                break;
            case "2":
                pmGradeText = "보통";
                break;
            case "3":
                pmGradeText = "나쁨";
                break;
            case "4":
                pmGradeText = "매우나쁨";
                break;
        }

        gradeText.setValue(pmGradeText);

        return gradeText;
    }

    public LiveData<LineData> makeHourlyChart(List<HourlyModel> list) {
        MutableLiveData<LineData> lineData = new MutableLiveData<>();
        ArrayList<Entry> hourlyTempList = new ArrayList<>();

        for (HourlyModel hour : list) {
            hourlyTempList.add(new Entry(hour.dt, kelvinToCelsius((float) hour.temp)));

            if (hourlyTempList.size() >= 16) {
                break;
            }
        }

        LineDataSet dataSet = new LineDataSet(hourlyTempList, "시간대 온도");

        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.1f);
        dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(true);
        dataSet.setLineWidth(1.8f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setDrawValues(true);
        dataSet.setValueFormatter(new DataLabelFormatter());
        dataSet.setValueTextSize(9f);
        dataSet.setColor(Color.BLACK);
        dataSet.setFillColor(Color.BLACK);
        dataSet.setFillAlpha(50);
        dataSet.setDrawHorizontalHighlightIndicator(false);

        lineData.setValue(new LineData(dataSet));

        return lineData;
    }

    public float kelvinToCelsius(float kelvin) {
        return Math.round((kelvin - 273.15f) * 10f) / 10f;
    }

    public String kelvinToCelsius(double kelvin) {
        return String.valueOf(kelvinToCelsius((float) kelvin)).concat("°C");
    }

}