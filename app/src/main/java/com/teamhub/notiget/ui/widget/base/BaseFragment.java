package com.teamhub.notiget.ui.widget.base;

import android.view.View;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected final View parentView;

    public static BaseFragment newInstance(View v) {
        return new BaseFragment(v);
    }

    public BaseFragment(View v) {
        parentView = v;
    }

}
