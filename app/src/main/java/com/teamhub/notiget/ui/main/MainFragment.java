package com.teamhub.notiget.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teamhub.notiget.R;
import com.teamhub.notiget.SettingsActivity;
import com.teamhub.notiget.adapter.main.WidgetListAdapter;
import com.teamhub.notiget.helper.PreferenceHelper;
import com.teamhub.notiget.ui.base.BaseFragment;

public class MainFragment extends BaseFragment {

    private MainViewModel viewModel;
    private View root;

    private ImageButton topBarSettingButton;
    private TextView titleDate;
    private TextView titleHighlight;

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
        setLiveHighlightData(new MutableLiveData<>());

        initReferences();
        initObservers();
        initEvents();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        String json = PreferenceHelper.getPreference(getContext(), "widgetConfig", "");
        viewModel.initWidgetConfig(json).observe(getViewLifecycleOwner(), s -> {
            viewModel.makeWidgetList(s);
            PreferenceHelper.setPreference(getContext(), "widgetConfig", s);
        });
    }

    private void initReferences() {
        topBarSettingButton = (ImageButton) root.findViewById(R.id.TopBarSettingButton);
        titleDate = (TextView) root.findViewById(R.id.TitleDate);
        titleHighlight = (TextView) root.findViewById(R.id.TitleHighlight);

        widgetListView = (RecyclerView) root.findViewById(R.id.WidgetListView);
        widgetListAdapter = new WidgetListAdapter(this);
        widgetListLayoutManager = new LinearLayoutManager(this.getContext());

        widgetListView.setHasFixedSize(true);
        widgetListView.setAdapter(widgetListAdapter);
        widgetListView.setLayoutManager(widgetListLayoutManager);
    }

    private void initObservers() {
        liveHighlightData.observe(getViewLifecycleOwner(), s -> {
            titleHighlight.setText(s);
        });

        viewModel.currentDate().observe(getViewLifecycleOwner(), s -> {
            titleDate.setText(s);
        });

        viewModel.widgetList.observe(getViewLifecycleOwner(), widgets -> {
            widgetListAdapter.updateList(widgets);
        });
    }

    private void initEvents() {
        topBarSettingButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingsActivity.class));
        });
    }

}