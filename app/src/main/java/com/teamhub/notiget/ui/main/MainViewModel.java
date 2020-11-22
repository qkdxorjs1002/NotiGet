package com.teamhub.notiget.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.Widget;
import com.teamhub.notiget.ui.widget.calculator.CalculatorFragment;
import com.teamhub.notiget.ui.widget.contact.ContactFragment;
import com.teamhub.notiget.ui.widget.dday.DDayFragment;
import com.teamhub.notiget.ui.widget.memo.MemoFragment;
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
        widgets.add(new Widget(MemoFragment::newInstance, R.string.ui_widget_memo));
        widgets.add(new Widget(DDayFragment::newInstance, R.string.ui_widget_dday));
        widgets.add(new Widget(ContactFragment::newInstance, R.string.ui_widget_contact));
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