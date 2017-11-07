package com.chart.model;

/**
 * Created by kan212 on 17/11/3.
 */

public interface ChartData {

    /**
     * @see #setAxisXBottom(Axis)
     */
    public Axis getAxisXBottom();

    /**
     * Set horizontal axis at the bottom of the chart. Pass null to remove that axis.
     *
     * @param axisX
     */
    public void setAxisXBottom(Axis axisX);

    /**
     * @see #setAxisYLeft(Axis)
     */
    public Axis getAxisYLeft();

    /**
     * Set vertical axis on the left of the chart. Pass null to remove that axis.
     *
     * @param axisY
     */
    public void setAxisYLeft(Axis axisY);

    /**
     * @see #setAxisXTop(Axis)
     */
    public Axis getAxisXTop();

    /**
     * Set horizontal axis at the top of the chart. Pass null to remove that axis.
     *
     * @param axisX
     */
    public void setAxisXTop(Axis axisX);

    /**
     * @see #setAxisYRight(Axis)
     */
    public Axis getAxisYRight();

    /**
     * Set vertical axis on the right of the chart. Pass null to remove that axis.
     *
     * @param axisY
     */
    public void setAxisYRight(Axis axisY);
}
