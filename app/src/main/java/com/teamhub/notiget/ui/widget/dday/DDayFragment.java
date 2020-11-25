package com.teamhub.notiget.ui.widget.dday;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.teamhub.notiget.R;
import com.teamhub.notiget.ui.widget.base.BaseFragment;

import java.util.Calendar;

public class DDayFragment extends BaseFragment {

    private View root;
    private TextView dateTv;
    private ImageButton settingButton;

    private DatePickerDialog dialog;

    public static DDayFragment newInstance(View v) {
        return new DDayFragment(v);
    }

    public DDayFragment(View v) {
        super(v);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.widget_dday, container, false);

        dateTv = root.findViewById(R.id.the_day);
        settingButton = (ImageButton) parentView.findViewById(R.id.WidgetSettingButton);

        if (load() != 0)
            dateTv.setText((countdday(load()) + "일"));
        else
            dateTv.setText("");

        dialog = new DatePickerDialog(getContext(), listener, 2020, 11, 22);

        settingButton.setOnClickListener(v -> {
            dialog.show();
        });

        settingButton.setVisibility(View.VISIBLE);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //datepicker에서 확인 버튼을 눌렀을 때
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear - 1, dayOfMonth, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            save(calendar.getTimeInMillis());
            dateTv.setText((countdday(calendar.getTimeInMillis())) + "일");
        }
    };

    public int countdday(long dday) {
        try {
            Calendar todaCal = Calendar.getInstance();

            long oneday = 86400000; //하루에대한 밀리세컨드 //1000*60*60*24
            long today = todaCal.getTimeInMillis();//오늘을 밀리세컨드로
            long count = (dday - today) / oneday; //둘의 차이를 구하고 차이가 몇일인지 구함
            return (int) -count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long load() {
        SharedPreferences pref = getActivity().getSharedPreferences("dday", Context.MODE_PRIVATE);
        return pref.getLong("dday", 0);
    }

    public void save(long dday) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("dday", Context.MODE_PRIVATE).edit();
        editor.putLong("dday", dday);
        editor.commit();
    }

}