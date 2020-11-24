package com.teamhub.notiget.ui.widget.digitime;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        Handler mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg){
                Calendar cal = Calendar.getInstance();

                SimpleDateFormat hmformat = new SimpleDateFormat("hh시 mm분");
                SimpleDateFormat secformat = new SimpleDateFormat("ss초 a");
                String strTime = hmformat.format(cal.getTime());
                String strTime2 = secformat.format(cal.getTime());

                TextView digitime = (TextView) root.findViewById(R.id.digitime);
                TextView digitime2 = (TextView) root.findViewById(R.id.digitime2);
                digitime.setText(strTime);
                digitime2.setText(strTime2);
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