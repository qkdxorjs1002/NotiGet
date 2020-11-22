package com.teamhub.notiget.model.main;

import androidx.fragment.app.Fragment;

public class Widget {

    private final CreateNewInstance createNewInstance;
    private final int titleResourceId;

    public Widget(CreateNewInstance method, int resourceId) {
        this.createNewInstance = method;
        this.titleResourceId = resourceId;
    }

    public interface CreateNewInstance {
        Fragment create();
    }

    public Fragment getFragment() {
        return createNewInstance.create();
    }

    public int getTitleResourceId() {

        return this.titleResourceId;
    }
}
