package com.teamhub.notiget.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.teamhub.notiget.helper.UnixTimeStampConverter;


public class XAxisLabelFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {

        return UnixTimeStampConverter.unixToHH((long) value).concat("시");
    }

}
