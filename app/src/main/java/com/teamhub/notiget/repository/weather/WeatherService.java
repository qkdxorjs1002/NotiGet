package com.teamhub.notiget.repository.weather;

import com.teamhub.notiget.model.weather.OneCallModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherService {

    @Headers({"Content-Type: charset=UTF-8"})
    @GET("onecall")
    Call<OneCallModel> getWeather(@Query("lat") double lat,
                                  @Query("lon") double lon,
                                  @Query("lang") String lang,
                                  @Query("exclude") String exclude,
                                  @Query("appid") String appid);

}
