package com.teamhub.notiget.adapter.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.main.Widget;
import com.teamhub.notiget.ui.widget.base.BaseFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetListAdapter extends RecyclerView.Adapter<WidgetListAdapter.ViewHolder> {

    private List<Widget> widgetList;

    private Map<String, BaseFragment> fragmentList;

    private final Fragment parentFragment;
    private OnItemClickListener onItemClickListener;

    public WidgetListAdapter(Fragment fragment) {
        parentFragment = fragment;
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public WidgetListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_widget_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView widgetTitle = (TextView) holder.view.findViewById(R.id.WidgetTitle);
        ImageButton widgetSettingButton = (ImageButton) holder.view.findViewById(R.id.WidgetSettingButton);

        Widget widget = widgetList.get(position);

        widgetTitle.setText(widget.getTitleResourceId());

        if (widget.onSettingClickListener != null) {
            widgetSettingButton.setOnClickListener(v -> {
                widget.onSettingClickListener.onSettingClick(parentFragment.getContext());
            });

            widgetSettingButton.setVisibility(View.VISIBLE);
        }

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v);
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        Widget widget = widgetList.get(holder.getAdapterPosition());

        if (fragmentList.get("fragment_".concat(String.valueOf(holder.getAdapterPosition()))) == null) {

            ConstraintLayout layout = (ConstraintLayout) holder.view.findViewById(R.id.WidgetFragmentContainer);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.topToTop = R.id.WidgetFragmentContainer;

            FragmentContainerView fragmentContainer = new FragmentContainerView(holder.view.getContext());
            fragmentContainer.setLayoutParams(layoutParams);
            fragmentContainer.setId(View.generateViewId());

            layout.addView(fragmentContainer);

            BaseFragment fragment = widget.getFragment();

            parentFragment.getChildFragmentManager().beginTransaction()
                    .replace(fragmentContainer.getId(), fragment)
                    .commitNow();

            fragmentList.put("fragment_".concat(String.valueOf(holder.getAdapterPosition())), fragment);
        }

    }

    @Override
    public int getItemCount() {
        if (widgetList == null) {

            return 0;
        }

        return widgetList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<Widget> list) {
        fragmentList = new HashMap<>();
        widgetList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
