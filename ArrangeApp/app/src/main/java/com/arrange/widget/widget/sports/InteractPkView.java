package com.arrange.widget.widget.sports;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.base.utils.DensityUtil;

/**
 * Created by kan212 on 17/10/26.
 */

public class InteractPkView extends View {

    RectF leftTextRect,rightTextRect,leftStripRect,rightStripRect,centerTextRect,rightOvalRect;
    Paint textPaint;
    Path leftPath,rightPath;
    float textRectWidth,centerPadding;


    public InteractPkView(Context context) {
        super(context);
        initView(context);
    }

    public InteractPkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public InteractPkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        initRect(changed, 0, 0, getWidth(), getHeight());
    }

    void initRect(boolean changed, int l, int t, int r, int b) {
        int cx = (r - l) / 2;
        int cy = (t - b) / 2;
        leftTextRect = new RectF(l,t,textRectWidth + l,b);
        leftStripRect = new RectF(textRectWidth,t,cx - centerPadding / 2,b);
//        leftStripRect = new RectF(40,40,140,140);

        float halfCenterPadding = centerPadding / 2;
        leftPath = new Path();
        leftPath.moveTo(cx - halfCenterPadding,90);
        leftPath.lineTo(cx - 20 - halfCenterPadding,140);
        leftPath.lineTo(textRectWidth,140);
        leftPath.lineTo(textRectWidth,90);
        leftPath.lineTo(cx - halfCenterPadding,90);
        leftPath.addCircle(textRectWidth,115,25, Path.Direction.CW);


        rightPath = new Path();
        rightPath.moveTo(cx + halfCenterPadding ,90);
        rightPath.lineTo(cx + halfCenterPadding + 20,140);
        rightPath.lineTo(cx * 2 - textRectWidth,140);
        rightPath.lineTo(cx * 2 - textRectWidth,90);
        rightPath.lineTo(cx + halfCenterPadding,90);
//        rightPath.addCircle(cx * 2 - textRectWidth,115,25, Path.Direction.CCW);

        rightOvalRect = new RectF(cx * 2 - textRectWidth - 25,90,cx * 2 - textRectWidth + 25,140);
        rightPath.addOval(rightOvalRect, Path.Direction.CCW);
    }

    private void initView(Context context) {

        textRectWidth = DensityUtil.dip2px(context,41);
        centerPadding = DensityUtil.dip2px(context,6);

        textPaint = new Paint();
        textPaint.setTextSize(26);
        textPaint.setColor(Color.RED);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawStrip(canvas);
    }

    /**
     * 绘制左右的文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.drawText("32.5",leftTextRect.left,leftTextRect.top,textPaint);
        canvas.drawText("40.3",leftTextRect.right - 100,100,textPaint);
    }

    /**
     * 绘制左右的对比
     *
     * @param canvas
     */
    private void drawStrip(Canvas canvas) {
//        canvas.drawArc(leftStripRect,270,90,true,textPaint);
        canvas.drawPath(leftPath,textPaint);
        canvas.drawPath(rightPath,textPaint);
    }
}
