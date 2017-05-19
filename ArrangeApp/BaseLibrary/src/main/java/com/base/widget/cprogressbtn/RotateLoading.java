package com.base.widget.cprogressbtn;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.base.R;


/**
 * Created by kan212 on 16/4/27.
 */
public class RotateLoading extends View {

    private static final int DEFAULT_WIDTH = 6;
    private static final int DEFAULT_SHADOW_POSITION = 2;
    private static final int DEFAULT_SPEED_OF_DEGREE = 10;

    private Paint mPaint,mBackgroudPaint;

    private RectF loadingRectF;

    private int topDegree = 10;
    private int bottomDegree = 190;

    private float arc;

    private int width;

    private boolean changeBigger = true;

    private int shadowPosition;

    private boolean isStart = false;

    private int color;

    private int speedOfDegree;

    private float speedOfArc;

    public RotateLoading(Context context) {
        super(context);
        initView(context, null);
    }

    public RotateLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RotateLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        color = Color.WHITE;
        width = dpToPx(context, DEFAULT_WIDTH);
        shadowPosition = dpToPx(getContext(), DEFAULT_SHADOW_POSITION);
        speedOfDegree = DEFAULT_SPEED_OF_DEGREE;

        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateLoading);
            color = typedArray.getColor(R.styleable.RotateLoading_loading_color, Color.WHITE);
            width = typedArray.getDimensionPixelSize(R.styleable.RotateLoading_loading_width, dpToPx(context, DEFAULT_WIDTH));
            shadowPosition = typedArray.getInt(R.styleable.RotateLoading_shadow_position, DEFAULT_SHADOW_POSITION);
            speedOfDegree = typedArray.getInt(R.styleable.RotateLoading_loading_speed, DEFAULT_SPEED_OF_DEGREE);
            typedArray.recycle();
        }
        speedOfArc = speedOfDegree / 4;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBackgroudPaint = new Paint();
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        arc = 10;
        loadingRectF = new RectF(2 * width, 2 * width, w - 2 * width, h - 2 * width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isStart) {
            return;
        }
        mPaint.setColor(color);
        canvas.drawArc(loadingRectF, topDegree, arc, false, mPaint);
        canvas.drawArc(loadingRectF, bottomDegree, arc, false, mPaint);
        topDegree += speedOfDegree;
        bottomDegree += speedOfDegree;
        if (topDegree > 360) {
            topDegree = topDegree - 360;
        }
        if (bottomDegree > 360) {
            bottomDegree = bottomDegree - 360;
        }

        if (changeBigger) {
            if (arc < 140) {
                arc += speedOfArc;
                invalidate();
            }
        } else {
            if (arc > speedOfDegree) {
                arc -= 2 * speedOfArc;
                invalidate();
            }
        }
        if (arc >= 140 || arc <= 10) {
            changeBigger = !changeBigger;
            invalidate();
        }
    }

    public void setLoadingColor(int color) {
        this.color = color;
    }

    public int getLoadingColor() {
        return color;
    }

    public void start() {
        isStart = true;
        invalidate();
    }

    public void stop() {
        isStart = false;
        invalidate();
    }

    public boolean isStart() {
        return isStart;
    }


    public int dpToPx(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }
}
