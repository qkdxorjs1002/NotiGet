package com.teamhub.notiget.model.settings;

import com.google.gson.annotations.SerializedName;

public class WidgetState {

    @SerializedName("name")
    public String name;

    @SerializedName("enabled")
    public boolean enabled;

    @SerializedName("titleResourceId")
    public final int titleResourceId;

    public WidgetState(String name, boolean enabled, int titleResourceId) {
        this.name = name;
        this.enabled = enabled;
        this.titleResourceId = titleResourceId;
    }

}
