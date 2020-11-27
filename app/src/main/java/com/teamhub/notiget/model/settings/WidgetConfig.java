package com.teamhub.notiget.model.settings;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WidgetConfig {

    @SerializedName("list")
    public List<WidgetState> list;

    public WidgetConfig() {
        list = new ArrayList<>();
    }

}
