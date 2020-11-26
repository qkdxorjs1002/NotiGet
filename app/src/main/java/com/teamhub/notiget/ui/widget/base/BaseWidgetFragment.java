package com.teamhub.notiget.ui.widget.base;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.teamhub.notiget.ui.base.BaseFragment;

import java.util.Map;

public class BaseWidgetFragment extends BaseFragment {

    protected final View parentView;

    public static BaseWidgetFragment newInstance(View v) {
        return new BaseWidgetFragment(v);
    }

    public BaseWidgetFragment(View v) {
        parentView = v;
    }
}
