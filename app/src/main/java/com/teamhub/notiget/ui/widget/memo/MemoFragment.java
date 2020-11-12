package com.teamhub.notiget.ui.widget.memo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

public class MemoFragment extends Fragment {

    private MemoViewModel viewModel;
    private View root;

    public static MemoFragment newInstance() {
        return new MemoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MemoViewModel.class);
        root = inflater.inflate(R.layout.widget_memo, container, false);

        // TODO: init reference

        return root;
    }
}