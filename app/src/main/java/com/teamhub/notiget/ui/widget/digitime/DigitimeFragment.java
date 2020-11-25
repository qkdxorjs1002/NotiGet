package com.teamhub.notiget.ui.widget.digitime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;
import com.teamhub.notiget.ui.widget.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rm.com.clocks.Clock;
import rm.com.clocks.ClockDrawable;
import rm.com.clocks.Stroke;

public class DigitimeFragment extends BaseFragment {

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

        ImageView clocks = (ImageView) root.findViewById(R.id.clocks);
        ClockDrawable clockDrawable = ClockDrawable.builder(getActivity())
                .hours(Calendar.HOUR)
                .minutes(Calendar.MINUTE)
                .withSpeed(-2.5F)
                .withColor(Color.WHITE)
                .withFrameWidth(Stroke.REGULAR)
                .withPointerWidth(Stroke.THIN)
                .withDuration(600L)
                .withInterpolator(new DecelerateInterpolator()).withListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                }).into(clocks);
        Handler mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg){
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat hmformat = new SimpleDateFormat("a hh:mm");
                SimpleDateFormat secformat = new SimpleDateFormat("ss");
                String strTime = hmformat.format(cal.getTime());
                String strTime2 = secformat.format(cal.getTime());

                TextView digitime = (TextView) root.findViewById(R.id.digitime);
                TextView digitime2 = (TextView) root.findViewById(R.id.digitime2);
                digitime.setText(strTime);
                digitime2.setText(strTime2);
                SimpleDateFormat hou = new SimpleDateFormat("hh");
                SimpleDateFormat min = new SimpleDateFormat("mm");
                int hours = Integer.parseInt(hou.format(cal.getTime()));
                int minutes = Integer.parseInt(min.format(cal.getTime()));
                clockDrawable.animateToTime(hours,minutes);
            }
        };
        class NewRunnable implements Runnable {

            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();

        return root;
    }
}