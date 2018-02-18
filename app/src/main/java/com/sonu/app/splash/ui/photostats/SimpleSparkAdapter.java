package com.sonu.app.splash.ui.photostats;

import com.robinhood.spark.SparkAdapter;

import java.util.List;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public class SimpleSparkAdapter extends SparkAdapter {

    private List<Integer> yData;

    public SimpleSparkAdapter(List<Integer> yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.size();
    }

    @Override
    public Object getItem(int index) {
        return yData.get(index);
    }

    @Override
    public float getY(int index) {
        return yData.get(index);
    }
}
