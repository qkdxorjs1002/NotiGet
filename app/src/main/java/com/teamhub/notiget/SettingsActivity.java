package com.teamhub.notiget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.teamhub.notiget.adapter.settings.SettingsListAdapter;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView widgetListView;
    private SettingsListAdapter widgetListAdapter;
    private LinearLayoutManager widgetListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        widgetListView = (RecyclerView) findViewById(R.id.SettingsWidgetListView);
        widgetListAdapter = new SettingsListAdapter(this);
        widgetListLayoutManager = new LinearLayoutManager(this);

        widgetListView.setHasFixedSize(false);
        widgetListView.setAdapter(widgetListAdapter);
        widgetListView.setLayoutManager(widgetListLayoutManager);
    }
}