package com.teamhub.notiget.repository.weather.dust;

import com.teamhub.notiget.model.weather.dust.DustModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DustService {

    @GET("air/data/dataJSON/AIR_DONG_DATA_KIOT_{city}.json")
    Call<DustModel> getDust(@Path("city") String city);
    
}
