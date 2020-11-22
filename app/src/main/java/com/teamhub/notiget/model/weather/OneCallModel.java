package com.teamhub.notiget.model.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneCallModel {

    public CurrentModel current;
    public List<DailyModel> daily;
    public double lat;
    public double lon;
    public String timezone;
    public String timezone_offset;

}
