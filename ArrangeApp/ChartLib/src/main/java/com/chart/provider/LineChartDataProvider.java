package com.chart.provider;

import com.chart.model.LineChartData;

/**
 * Created by kan212 on 17/11/3.
 */

public interface LineChartDataProvider {

    public LineChartData getLineChartData();

    public void setLineChartData(LineChartData data);
}
