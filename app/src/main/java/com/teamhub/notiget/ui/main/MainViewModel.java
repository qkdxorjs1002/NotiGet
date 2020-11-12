package com.teamhub.notiget.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.teamhub.notiget.R;
import com.teamhub.notiget.model.Widget;
import com.teamhub.notiget.ui.widget.findroute.FindRouteFragment;
import com.teamhub.notiget.ui.widget.memo.MemoFragment;
import com.teamhub.notiget.ui.widget.screentime.ScreenTimeFragment;
import com.teamhub.notiget.ui.widget.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

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
        widgets.add(new Widget(FindRouteFragment::newInstance, R.string.ui_widget_findroute));

        this.widgetList.postValue(widgets);
    }

}