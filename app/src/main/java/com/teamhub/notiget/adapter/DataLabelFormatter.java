package com.teamhub.notiget.adapter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;


public class DataLabelFormatter extends ValueFormatter {

    private DecimalFormat decimalFormat;

    public DataLabelFormatter() {
        decimalFormat = new DecimalFormat("###,##0.0");
    }

    @Override
    public String getPointLabel(Entry entry) {

        return decimalFormat.format(entry.getY());
    }
}