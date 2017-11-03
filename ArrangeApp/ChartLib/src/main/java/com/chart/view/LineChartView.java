package com.chart.view;

import android.content.Context;
import android.util.AttributeSet;

import com.chart.model.LineChartData;
import com.chart.provider.LineChartDataProvider;

/**
 * Created by kan212 on 17/11/3.
 * LineChart, supports cubic lines, filled lines, circle and square points. Point radius and stroke width can be
 * adjusted using LineChartData attributes
 */

public class LineChartView extends AbstractChartView implements LineChartDataProvider{

    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public void setInteractive(boolean isInteractive) {

    }


    @Override
    public LineChartData getLineChartData() {
        return null;
    }

    @Override
    public void setLineChartData(LineChartData data) {

    }
}
