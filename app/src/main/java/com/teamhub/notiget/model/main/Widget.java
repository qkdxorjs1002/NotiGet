package com.teamhub.notiget.model.main;

import android.view.View;

import com.teamhub.notiget.ui.widget.base.BaseWidgetFragment;

public class Widget {

    private final CreateNewInstance createNewInstance;
    private final int titleResourceId;

    public Widget(CreateNewInstance method, int resourceId) {
        this.createNewInstance = method;
        this.titleResourceId = resourceId;
    }

    public interface CreateNewInstance {
        BaseWidgetFragment create(View v);
    }

    public BaseWidgetFragment getFragment(View v) {
        return createNewInstance.create(v);
    }

    public int getTitleResourceId() {

        return this.titleResourceId;
    }
}
