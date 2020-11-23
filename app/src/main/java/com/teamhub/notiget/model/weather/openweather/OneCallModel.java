package com.teamhub.notiget.model.weather.openweather;

import java.util.List;

public class OneCallModel {

    public CurrentModel current;
    public List<DailyModel> daily;
    public List<HourlyModel> hourly;
    public double lat;
    public double lon;
    public String timezone;
    public String timezone_offset;

}
