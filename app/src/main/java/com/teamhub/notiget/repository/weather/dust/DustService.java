package com.teamhub.notiget.repository.weather.dust;

import com.teamhub.notiget.model.weather.dust.DustModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DustService {

    @GET("api/air_1hr_{city}.xml")
    Call<DustModel> getDust(@Path("city") String city);
    
}
