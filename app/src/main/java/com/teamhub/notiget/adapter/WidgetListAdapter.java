package com.teamhub.notiget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.Widget;

import java.util.List;

public class WidgetListAdapter extends RecyclerView.Adapter<WidgetListAdapter.ViewHolder> {

    private List<Widget> widgetList;

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

        ConstraintLayout layout = (ConstraintLayout) holder.view.findViewById(R.id.WidgetContainer);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.topToBottom = R.id.WidgetTitle;

        FragmentContainerView fragmentContainer = new FragmentContainerView(holder.view.getContext());
        fragmentContainer.setLayoutParams(layoutParams);
        fragmentContainer.setId(View.generateViewId());

        layout.addView(fragmentContainer);

        parentFragment.getChildFragmentManager().beginTransaction()
                .add(fragmentContainer.getId(), widget.getFragment())
                .commitNow();
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
