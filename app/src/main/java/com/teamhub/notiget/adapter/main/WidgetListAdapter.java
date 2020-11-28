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
import com.teamhub.notiget.ui.base.BaseFragment;
import com.teamhub.notiget.ui.widget.base.BaseWidgetFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetListAdapter extends RecyclerView.Adapter<WidgetListAdapter.ViewHolder> {

    private List<Widget> widgetList;

    private Map<String, BaseWidgetFragment> fragmentList;

    private final BaseFragment parentFragment;
    private OnItemClickListener onItemClickListener;

    public WidgetListAdapter(BaseFragment fragment) {
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

        Widget widget = widgetList.get(position);

        widgetTitle.setText(widget.getTitleResourceId());

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

        if (fragmentList.get(String.valueOf(holder.getAdapterPosition())) == null) {

            ConstraintLayout layout = (ConstraintLayout) holder.view.findViewById(R.id.WidgetFragmentContainer);
            layout.removeAllViews();

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.topToTop = R.id.WidgetFragmentContainer;

            FragmentContainerView fragmentContainer = new FragmentContainerView(holder.view.getContext());
            fragmentContainer.setLayoutParams(layoutParams);
            fragmentContainer.setId(View.generateViewId());

            layout.addView(fragmentContainer);

            BaseWidgetFragment fragment = widget.getFragment(holder.view);
            fragment.setLiveMapData(parentFragment.getLiveMapData());
            if (holder.getAdapterPosition() == 0) {
                fragment.setLiveHighlightData(parentFragment.getLiveHighlightData());
            }

            parentFragment.getChildFragmentManager()
                    .beginTransaction()
                    .replace(fragmentContainer.getId(), fragment)
                    .commitNow();

            fragmentList.put(String.valueOf(holder.getAdapterPosition()), fragment);
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
