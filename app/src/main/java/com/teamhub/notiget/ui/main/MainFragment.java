package com.teamhub.notiget.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamhub.notiget.R;
import com.teamhub.notiget.adapter.WidgetListAdapter;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;
    private View root;

    private TextView titleDate;

    private RecyclerView widgetListView;
    private WidgetListAdapter widgetListAdapter;
    private LinearLayoutManager widgetListLayoutManager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        root = inflater.inflate(R.layout.main_fragment, container, false);

        initReferences();
        initObservers();
        initEvents();

        return root;
    }

    private void initReferences() {
        titleDate = (TextView) root.findViewById(R.id.TitleDate);

        widgetListView = (RecyclerView) root.findViewById(R.id.WidgetListView);
        widgetListAdapter = new WidgetListAdapter(this);
        widgetListLayoutManager = new LinearLayoutManager(this.getContext());

        widgetListView.setHasFixedSize(true);
        widgetListView.setAdapter(widgetListAdapter);
        widgetListView.setLayoutManager(widgetListLayoutManager);

        viewModel.commandMakeWidgetList();
    }

    private void initObservers() {
        viewModel.currentDate().observe(getViewLifecycleOwner(), s -> {
            titleDate.setText(s);
        });

        viewModel.widgetList.observe(getViewLifecycleOwner(), widgets -> {
            widgetListAdapter.updateList(widgets);
        });
    }

    private void initEvents() {

    }

}