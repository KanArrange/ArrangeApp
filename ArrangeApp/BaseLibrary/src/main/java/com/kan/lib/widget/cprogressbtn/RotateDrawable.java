package com.kan.lib.widget.cprogressbtn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by kan212 on 17/3/9.
 */

public class RotateDrawable extends View{

    private int color;
    private RectF loadingRectF;
    private int topDegree = 10;
    private int bottomDegree = 190;
    private Paint mPaint;
    private int  arc = 10;
    private int speedOfDegree = 10;
    private boolean changeBigger = true;
    private float speedOfArc;
    private int width;
    private View mParent;


    public RotateDrawable(View parent,Context context,int width){
        super(context);
        color = Color.WHITE;
        loadingRectF = new RectF(0,0,width,width);
        this.mParent = parent;
        this.width = width;
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
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
                mParent.postInvalidate();
            }
        } else {
            if (arc > speedOfDegree) {
                arc -= 2 * speedOfArc;
                mParent.postInvalidate();
            }
        }
        if (arc >= 140 || arc <= 10) {
            changeBigger = !changeBigger;
            mParent.postInvalidate();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        invalidate();
    }

    public void start(){
        invalidate();
    }

}
