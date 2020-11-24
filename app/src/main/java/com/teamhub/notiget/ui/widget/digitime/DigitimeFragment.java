package com.teamhub.notiget.ui.widget.digitime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class DigitimeFragment extends Fragment {

    private DigitimeViewModel viewModel;
    private View root;

    public static DigitimeFragment newInstance() {
        return new DigitimeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DigitimeViewModel.class);
        root = inflater.inflate(R.layout.widget_digitime, container, false);

        // TODO: init reference

        return root;
    }
}