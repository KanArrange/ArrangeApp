package com.chart.listener;

import com.chart.model.Viewport;

/**
 * Created by kan212 on 17/11/7.
 * Use implementations of this listener to be notified when chart viewport changed. For now it works only for preview
 * charts. To make it works for other chart types you just need to uncomment last line in
 * {@link ChartComputator#constrainViewport(float, float, float, float)} method.
 */

public interface ViewportChangeListener {
    /**
     * Called when current viewport of chart changed. You should not modify that viewport.
     */
    public void onViewportChanged(Viewport viewport);

}
