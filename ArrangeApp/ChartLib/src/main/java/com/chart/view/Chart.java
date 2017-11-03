package com.chart.view;

/**
 * Created by kan212 on 17/11/3.
 */

public interface Chart {

    /**
     * Returns true if chart is interactive.
     *
     * @see #setInteractive(boolean)
     */
    public boolean isInteractive();

    /**
     * Set true to allow user use touch gestures. If set to false user will not be able zoom, scroll or select/touch
     * value. By default true.
     */
    public void setInteractive(boolean isInteractive);
}
