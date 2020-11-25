package com.teamhub.notiget.model.main;

import android.content.Context;

import com.teamhub.notiget.ui.widget.base.BaseFragment;

public class Widget {

    private final CreateNewInstance createNewInstance;
    private final int titleResourceId;
    public OnSettingClickListener onSettingClickListener;

    public Widget(CreateNewInstance method, int resourceId, OnSettingClickListener onClickListener) {
        this.createNewInstance = method;
        this.titleResourceId = resourceId;
        this.onSettingClickListener = onClickListener;
    }

    public interface CreateNewInstance {
        BaseFragment create();
    }

    public interface OnSettingClickListener {
        void onSettingClick(Context context);
    }

    public BaseFragment getFragment() {
        return createNewInstance.create();
    }

    public int getTitleResourceId() {

        return this.titleResourceId;
    }
}
