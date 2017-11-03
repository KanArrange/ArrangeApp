package com.chart.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.chart.animation.ChartDataAnimator;
import com.chart.animation.ChartDataAnimatorV14;
import com.chart.animation.ChartDataAnimatorV8;
import com.chart.animation.ChartViewportAnimator;
import com.chart.animation.ChartViewportAnimatorV14;
import com.chart.animation.ChartViewportAnimatorV8;
import com.chart.computator.ChartComputator;
import com.chart.gesture.ChartTouchHandler;
import com.chart.gesture.ContainerScrollType;
import com.chart.provider.AxesRenderer;
import com.chart.provider.ChartRenderer;

/**
 * Created by kan212 on 17/11/3.
 */

public abstract class AbstractChartView extends View implements Chart {

    protected ChartComputator chartComputator;
    protected AxesRenderer axesRenderer;
    protected ChartTouchHandler touchHandler;
    protected ChartRenderer chartRenderer;
    protected ChartDataAnimator dataAnimator;
    protected ChartViewportAnimator viewportAnimator;
    protected boolean isInteractive = true;
    protected boolean isContainerScrollEnabled = false;
    protected ContainerScrollType containerScrollType;


    public AbstractChartView(Context context) {
        this(context, null, 0);
    }

    public AbstractChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbstractChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        chartComputator = new ChartComputator();
        touchHandler = new ChartTouchHandler(context,this);
        axesRenderer = new AxesRenderer(context,this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.dataAnimator = new ChartDataAnimatorV8(this);
            this.viewportAnimator = new ChartViewportAnimatorV8(this);
        } else {
            this.viewportAnimator = new ChartViewportAnimatorV14(this);
            this.dataAnimator = new ChartDataAnimatorV14(this);
        }

    }


}
