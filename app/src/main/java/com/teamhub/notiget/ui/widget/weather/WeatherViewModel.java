package com.teamhub.notiget.ui.widget.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamhub.notiget.model.weather.OneCallModel;
import com.teamhub.notiget.repository.weather.WeatherRepository;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;
    public MutableLiveData<OneCallModel> weatherData;

    public WeatherViewModel() {
        weatherRepository = new WeatherRepository();
        weatherData = new MutableLiveData<>();
    }

    public void getWeather(double lat, double lon) {
        weatherData = weatherRepository.getWeather(lat, lon);
    }

}