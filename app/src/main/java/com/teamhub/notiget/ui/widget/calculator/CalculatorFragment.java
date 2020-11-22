package com.teamhub.notiget.ui.widget.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel viewModel;
    private View root;

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        root = inflater.inflate(R.layout.widget_calculator, container, false);

        // TODO: init reference

        return root;
    }
}