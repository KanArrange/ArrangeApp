package com.chart.animation;

import java.util.EventListener;

/**
 * Created by kan212 on 17/11/7.
 * Listener used to listen for chart animation start and stop events. Implementations of this interface can be used for
 * all types of chart animations(data, viewport, PieChart rotation).
 */

public interface ChartAnimationListener extends EventListener {

    public void onAnimationStarted();

    public void onAnimationFinished();


}
