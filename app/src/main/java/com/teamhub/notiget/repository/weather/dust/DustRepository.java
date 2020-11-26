package com.teamhub.notiget.repository.weather.dust;

import androidx.lifecycle.MutableLiveData;

import com.teamhub.notiget.model.weather.dust.DustModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class DustRepository {

    Retrofit retrofit;
    DustService service;

    private Map<String, String> cityMap = new HashMap<String, String>() {{
        put("서울특별시", "1100000000");
        put("부산광역시", "2600000000");
        put("대구광역시", "2700000000");
        put("인천광역시", "2800000000");
        put("광주광역시", "2900000000");
        put("대전광역시", "3000000000");
        put("울산광역시", "3100000000");
        put("세종특별자치시", "3600000000");
        put("경기도", "4100000000");
        put("강원도", "4200000000");
        put("충청북도", "4300000000");
        put("충청남도", "4400000000");
        put("전라북도", "4500000000");
        put("전라남도", "4600000000");
        put("경상북도", "4700000000");
        put("경상남도", "4800000000");
        put("제주특별자치도", "5000000000");
    }};

    public DustRepository() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://kweather.co.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DustService.class);
    }

    public MutableLiveData<DustModel> getDust(String city) {
        MutableLiveData<DustModel> dustData = new MutableLiveData<>();

        Call<DustModel> dustCall = service.getDust(cityMap.get(city));

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
