package com.teamhub.notiget.adapter.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.GsonBuilder;
import com.teamhub.notiget.R;
import com.teamhub.notiget.helper.PreferenceHelper;
import com.teamhub.notiget.model.settings.WidgetConfig;
import com.teamhub.notiget.model.settings.WidgetState;

public class SettingsListAdapter extends RecyclerView.Adapter<SettingsListAdapter.ViewHolder> {

    private Context context;
    private WidgetConfig widgetConfig;

    public SettingsListAdapter(Context context) {
        this.context = context;
        String json = PreferenceHelper.getPreference(context, "widgetConfig", "");
        widgetConfig = new GsonBuilder().create().fromJson(json, WidgetConfig.class);
    }

    @NonNull
    @Override
    public SettingsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_settings_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView widgetTitle = (TextView) holder.view.findViewById(R.id.WidgetTitle);
        ImageButton widgetUpButton = (ImageButton) holder.view.findViewById(R.id.WidgetUpButton);
        ImageButton widgetDownButton = (ImageButton) holder.view.findViewById(R.id.WidgetDownButton);
        SwitchMaterial widgetEnableSwitch = (SwitchMaterial) holder.view.findViewById(R.id.WidgetEnableSwitch);

        widgetTitle.setText(widgetConfig.list.get(position).titleResourceId);
        widgetUpButton.setEnabled(position != 0);
        widgetDownButton.setEnabled(position != widgetConfig.list.size() - 1);
        widgetEnableSwitch.setChecked(widgetConfig.list.get(position).enabled);

        widgetUpButton.setOnClickListener(v -> {
            upWidget(position);
            savePreference();
        });

        widgetDownButton.setOnClickListener(v -> {
            downWidget(position);
            savePreference();
        });

        widgetEnableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            widgetConfig.list.get(position).enabled = isChecked;
            savePreference();
        });
    }

    @Override
    public int getItemCount() {
        if (widgetConfig.list == null) {

            return 0;
        }

        return widgetConfig.list.size();
    }

    private void upWidget(int position) {
        WidgetState widget = widgetConfig.list.get(position);
        widgetConfig.list.remove(position);
        widgetConfig.list.add(position - 1, widget);
        notifyDataSetChanged();
    }

    private void downWidget(int position) {
        WidgetState widget = widgetConfig.list.get(position);
        widgetConfig.list.remove(position);
        widgetConfig.list.add(position + 1, widget);
        notifyDataSetChanged();
    }

    private void savePreference() {
        PreferenceHelper.setPreference(context, "widgetConfig",
                new GsonBuilder().create().toJson(widgetConfig));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

}
