package com.teamhub.notiget.repository.weather.openweather;

import androidx.lifecycle.MutableLiveData;

import com.teamhub.notiget.model.weather.openweather.OneCallModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {

    Retrofit retrofit;
    WeatherService service;

    public WeatherRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(WeatherService.class);
    }

    public MutableLiveData<OneCallModel> getWeather(double lat, double lon) {
        MutableLiveData<OneCallModel> weatherData = new MutableLiveData<>();

        Call<OneCallModel> weatherCall = service.getWeather(lat, lon, "kr",
                "minutely,alerts", "87008e1379858d1033dc7b454669ff96");

        weatherCall.enqueue(new Callback<OneCallModel>() {
            @Override
            public void onResponse(Call<OneCallModel> call, Response<OneCallModel> response) {
                weatherData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OneCallModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return weatherData;
    }
}
