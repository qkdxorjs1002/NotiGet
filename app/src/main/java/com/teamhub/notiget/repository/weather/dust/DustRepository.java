package com.teamhub.notiget.repository.weather.dust;

import androidx.lifecycle.MutableLiveData;

import com.teamhub.notiget.model.weather.dust.DustModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class DustRepository {

    Retrofit retrofit;
    DustService service;

    public DustRepository() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://kweather.co.kr/air/data/")
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();

        service = retrofit.create(DustService.class);
    }

    public MutableLiveData<DustModel> getDust(String city) {
        MutableLiveData<DustModel> dustData = new MutableLiveData<>();

        Call<DustModel> dustCall = service.getDust(city);

        dustCall.enqueue(new Callback<DustModel>() {
            @Override
            public void onResponse(@NotNull Call<DustModel> call, Response<DustModel> response) {
                dustData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DustModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return dustData;
    }
}
