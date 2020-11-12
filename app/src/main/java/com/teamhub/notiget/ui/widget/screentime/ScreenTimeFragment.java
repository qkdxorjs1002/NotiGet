package com.teamhub.notiget.ui.widget.screentime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class ScreenTimeFragment extends Fragment {

    private ScreenTimeViewModel viewModel;
    private View root;

    public static ScreenTimeFragment newInstance() {
        return new ScreenTimeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ScreenTimeViewModel.class);
        root = inflater.inflate(R.layout.widget_screentime, container, false);

        // TODO: init reference

        return root;
    }
}