package com.teamhub.notiget.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.GsonBuilder;
import com.teamhub.notiget.R;
import com.teamhub.notiget.model.main.Widget;
import com.teamhub.notiget.model.settings.WidgetConfig;
import com.teamhub.notiget.model.settings.WidgetState;
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

    public MutableLiveData<String> initWidgetConfig(String json) {
        if (json.isEmpty()) {
            WidgetConfig widgetConfig = new WidgetConfig();

            widgetConfig.list.add(new WidgetState(
                    "weather", true, R.string.ui_widget_weather));
            widgetConfig.list.add(new WidgetState(
                    "screentime", true, R.string.ui_widget_screentime));
            widgetConfig.list.add(new WidgetState(
                    "digitime", true, R.string.ui_widget_digitime));
            widgetConfig.list.add(new WidgetState(
                    "dday", true, R.string.ui_widget_dday));
            widgetConfig.list.add(new WidgetState(
                    "calculator", true, R.string.ui_widget_calculator));

            return new MutableLiveData<>(new GsonBuilder().create().toJson(widgetConfig));
        }

        return new MutableLiveData<>(json);
    }

    public void makeWidgetList(String json) {
        WidgetConfig widgetConfig = new GsonBuilder().create().fromJson(json, WidgetConfig.class);

        List<Widget> widgets = new ArrayList<>();

        for (WidgetState widgetState : widgetConfig.list) {
            if (widgetState.enabled) {
                switch (widgetState.name) {
                    case "weather":
                        widgets.add(new Widget(
                                WeatherFragment::newInstance,
                                widgetState.titleResourceId)
                        );
                        break;
                    case "screentime":
                        widgets.add(new Widget(
                                ScreenTimeFragment::newInstance,
                                widgetState.titleResourceId)
                        );
                        break;
                    case "digitime":
                        widgets.add(new Widget(
                                DigitimeFragment::newInstance,
                                widgetState.titleResourceId)
                        );
                        break;
                    case "dday":
                        widgets.add(new Widget(
                                DDayFragment::newInstance,
                                widgetState.titleResourceId)
                        );
                        break;
                    case "calculator":
                        widgets.add(new Widget(
                                CalculatorFragment::newInstance,
                                widgetState.titleResourceId)
                        );
                        break;
                }
            }
        }

        this.widgetList.postValue(widgets);
    }

    public LiveData<String> currentDate() {
        MutableLiveData<String> date = new MutableLiveData<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 d일 E요일", Locale.KOREA);
        date.setValue(simpleDateFormat.format(new Date(System.currentTimeMillis())));

        return date;
    }

}