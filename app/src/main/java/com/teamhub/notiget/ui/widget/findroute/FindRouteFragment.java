package com.teamhub.notiget.ui.widget.findroute;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class FindRouteFragment extends Fragment {

    private FindRouteViewModel viewModel;
    private View root;

    public static FindRouteFragment newInstance() {
        return new FindRouteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FindRouteViewModel.class);
        root = inflater.inflate(R.layout.widget_findroute, container, false);

        // TODO: init reference

        return root;
    }
}