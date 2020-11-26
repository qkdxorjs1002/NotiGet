package com.teamhub.notiget.ui.widget.weather;

import android.graphics.Color;
import android.location.Address;
import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.teamhub.notiget.formatter.DataLabelFormatter;
import com.teamhub.notiget.model.weather.dust.DongModel;
import com.teamhub.notiget.model.weather.dust.GuModel;
import com.teamhub.notiget.model.weather.openweather.HourlyModel;
import com.teamhub.notiget.model.weather.openweather.OneCallModel;
import com.teamhub.notiget.repository.weather.dust.DustRepository;
import com.teamhub.notiget.repository.weather.openweather.WeatherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final DustRepository dustRepository;
    public MutableLiveData<Location> location;
    public MutableLiveData<Address> address;
    public MutableLiveData<OneCallModel> weatherData;
    public MutableLiveData<DongModel> dustData;

    public WeatherViewModel() {
        weatherRepository = new WeatherRepository();
        dustRepository = new DustRepository();
        location = new MutableLiveData<>();
        address = new MutableLiveData<>();
        weatherData = new MutableLiveData<>();
        dustData = new MutableLiveData<>();
    }

    public void setLocationData(MutableLiveData<Map<String, Object>> liveMapData) {
        if (liveMapData != null) {
            Map<String, ?> map = liveMapData.getValue();

            if (map != null) {
                Location gps = (Location) map.get("gpsLocation");
                Location network = (Location) map.get("networkLocation");

                if (gps != null) {
                    location.postValue(gps);
                    address.postValue((Address) map.get("gpsAddress"));
                } else {
                    location.postValue(network);
                    address.postValue((Address) map.get("networkAddress"));
                }
            }
        }
    }

    public void getWeather(double lat, double lon) {
        weatherRepository.getWeather(lat, lon).observeForever(oneCallModel -> {
            weatherData.setValue(oneCallModel);
        });
    }

    public void getDust(Address address) {
        dustRepository.getDust(address.getAdminArea()).observeForever(dustModel -> {
            if (dustModel == null) {
                return;
            }

            String addressGu;
            String addressDong = address.getThoroughfare();
            if (address.getSubLocality() != null) {
                addressGu = address.getSubLocality();
            } else {
                addressGu = address.getLocality();
            }

            DongModel dongData = null;
            for (GuModel gu : dustModel.list.guList) {
                if (gu.gName.contains(addressGu)) {
                    for (DongModel dong : gu.dongList) {
                        dongData = dong;
                        if (dongData.dName.contains(addressDong)) {
                            break;
                        }
                    }
                    break;
                }
            }

            dustData.postValue(dongData);
        });
    }

    public LiveData<String> getDescription(Address address) {
        String addressGu;
        String addressDong = address.getThoroughfare();
        if (address.getSubLocality() != null) {
            addressGu = address.getSubLocality();
        } else {
            addressGu = address.getLocality();
        }

        return new MutableLiveData<>(addressGu.concat(" ").concat(addressDong));
    }

    public LiveData<String> gradeToText(int pmValue) {
        MutableLiveData<String> gradeText = new MutableLiveData<>();

        String pmGradeText = "관측없음";

        if (pmValue > 150) {
            pmGradeText = "매우나쁨";
        } else if (pmValue > 80) {
            pmGradeText = "나쁨";
        } else if (pmValue > 30) {
            pmGradeText = "보통";
        } else if (pmValue >= 0) {
            pmGradeText = "좋음";
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