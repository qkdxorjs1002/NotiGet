package com.teamhub.notiget.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.main.Widget;
import com.teamhub.notiget.ui.widget.calculator.CalculatorFragment;
import com.teamhub.notiget.ui.widget.dday.DDayFragment;
import com.teamhub.notiget.ui.widget.digitime.DigitimeFragment;
import com.teamhub.notiget.ui.widget.screentime.ScreenTimeFragment;
import com.teamhub.notiget.ui.widget.weather.WeatherFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<Widget>> widgetList;

    public MainViewModel() {
        widgetList = new MutableLiveData<>();
    }

    public void commandMakeWidgetList() {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(new Widget(WeatherFragment::newInstance, R.string.ui_widget_weather));
        widgets.add(new Widget(ScreenTimeFragment::newInstance, R.string.ui_widget_screentime));
        widgets.add(new Widget(DigitimeFragment::newInstance, R.string.ui_widget_digitime));
        widgets.add(new Widget(DDayFragment::newInstance, R.string.ui_widget_dday));
        widgets.add(new Widget(CalculatorFragment::newInstance, R.string.ui_widget_calculator));

        this.widgetList.postValue(widgets);
    }

    public LiveData<String> currentDate() {
        MutableLiveData<String> date = new MutableLiveData<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 d일 E요일", Locale.KOREA);
        date.setValue(simpleDateFormat.format(new Date(System.currentTimeMillis())));

        return date;
    }

}