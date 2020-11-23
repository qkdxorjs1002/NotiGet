package com.teamhub.notiget.adapter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.teamhub.notiget.tool.UnixTimeStampConverter;


public class XAxisLabelFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {

        return UnixTimeStampConverter.unixToHH((long) value).concat("시");
    }

}
