package com.arrange.widget.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kan212 on 16/4/20.
 * 学习，绘制一个小黄人出来
 */
public class MinionView extends View {

    private static final int DEFAULT_SIZE = 200; //View默认大小
    private int widthForUnspecified;
    private int heightForUnspecified;


    public MinionView(Context context) {
        super(context);
    }

    public MinionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MinionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MinionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
    }

    /**
     * 计算屏幕的时候取出来宽高
     * @param origin
     * @param isWidth
     * @return
     */
    private int measure(int origin,boolean isWidth) {
        int result;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                if (isWidth) {
                    widthForUnspecified = result;
                }else {
                    heightForUnspecified = result;
                }
                break;
            default:
                if (isWidth) {//宽或高未指定的情况下，可以由另一端推算出来 - -如果两边都没指定就用默认值
                    result = (int) (heightForUnspecified * BODY_WIDTH_HEIGHT_SCALE);
                } else {
                    result = (int) (widthForUnspecified / BODY_WIDTH_HEIGHT_SCALE);
                }
                if (result == 0) {
                    result = DEFAULT_SIZE;
                }
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initParams();
        initPaint();
        drawFeetShadow(canvas);//脚下的阴影
        drawFeet(canvas);//脚
        drawHands(canvas);//手
        drawBody(canvas);//画身体
    }

    private Paint mPaint;
    private float bodyWidth;
    private float bodyHeigh;
    private static final float BODY_SCALE = 0.6f;//身体主干占整个view的比重
    private static final float BODY_WIDTH_HEIGHT_SCALE = 0.6f; //        身体的比例设定为 w:h = 3:5

    private float mStrokeWidth = 4;//描边宽度
    private float offset;//计算时，部分需要 考虑找边偏移
    private RectF bodyRect;
    private float radius;
    private float footHeigh;//脚的高度，用来画脚部阴影时用
    private float handsHeight;//计算出吊带的高度时，可以用来做手的高度
    private int colorStroke = Color.BLACK;
    private int colorBody = Color.rgb(249, 217, 70);//衣服的颜色



    private void initParams() {
        bodyWidth = Math.min(getWidth(), getHeight() * BODY_WIDTH_HEIGHT_SCALE) * BODY_SCALE;
        bodyHeigh = Math.min(getWidth(), getHeight() * BODY_WIDTH_HEIGHT_SCALE) / BODY_WIDTH_HEIGHT_SCALE * BODY_SCALE;
        mStrokeWidth = Math.max(bodyWidth/50,mStrokeWidth);

        offset = mStrokeWidth / 2;
        bodyRect = new RectF();
        bodyRect.left = (getWidth() - bodyWidth) / 2;
        bodyRect.top = (getHeight() - bodyHeigh) / 2;
        bodyRect.right = bodyRect.left + bodyWidth;
        bodyRect.bottom = bodyRect.top + bodyHeigh;

        radius = bodyWidth / 2;
        footHeigh = radius * 0.4333f;
        handsHeight =  (getHeight() + bodyHeigh) / 2   + offset - radius * 1.65f ;
    }


    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        } else {
            mPaint.reset();
        }
        mPaint.setAntiAlias(true);//边缘无锯齿
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawFeetShadow(Canvas canvas) {
        mPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        canvas.drawOval(bodyRect.left + bodyWidth * 0.15f,bodyRect.bottom - offset + footHeigh,bodyRect.right - bodyWidth * 0.15f,bodyRect.bottom - offset + footHeigh + mStrokeWidth * 1.3f,mPaint);
    }

    private void drawFeet(Canvas canvas) {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(colorStroke);

        float radiusFoot = radius / 3 * 0.4f;
        float leftFootStartX = bodyRect.left + radius - offset * 2;
        float leftFootStartY = bodyRect.bottom - offset;
        float footWidthA = radius * 0.5f;//脚宽度大-到半圆结束
        float footWidthB = footWidthA / 3;//脚宽度-比较细的部分

        Path path = new Path();
        path.moveTo(leftFootStartX,leftFootStartY);
        path.lineTo(leftFootStartX,leftFootStartY + footHeigh);
        path.lineTo(leftFootStartX - footWidthA + radiusFoot,leftFootStartY + footHeigh);

        RectF rectF = new RectF();
        rectF.left = leftFootStartX - footWidthA;
        rectF.top = leftFootStartY +footHeigh - radiusFoot*2;
        rectF.bottom = rectF.top + radiusFoot * 2;
        rectF.right = rectF.left + radiusFoot * 2;
        path.addArc(rectF,90,180);
        path.lineTo(rectF.left + radiusFoot + footWidthB,rectF.top);
        path.lineTo(rectF.left + radiusFoot + footWidthB,leftFootStartY);
        path.lineTo(leftFootStartX,leftFootStartY);
        canvas.drawPath(path,mPaint);

        //右脚
        float rightFootStartX = bodyRect.left + radius + offset * 2;
        float rightFootStartY = leftFootStartY;
        path.reset();
        path.moveTo(rightFootStartX, rightFootStartY);
        path.lineTo(rightFootStartX, rightFootStartY + footHeigh);
        path.lineTo(rightFootStartX + footWidthA - radiusFoot, rightFootStartY + footHeigh);

        rectF.left = rightFootStartX + footWidthA - radiusFoot * 2;
        rectF.top = rightFootStartY + footHeigh - radiusFoot * 2;
        rectF.right = rectF.left + radiusFoot * 2;
        rectF.bottom = rectF.top + radiusFoot * 2;
        path.addArc(rectF, 90, -180);
        path.lineTo(rectF.right - radiusFoot - footWidthB, rectF.top);
        path.lineTo(rectF.right - radiusFoot - footWidthB, rightFootStartY);
        path.lineTo(rightFootStartX, rightFootStartY);
        canvas.drawPath(path, mPaint);
    }

    private void drawBody(Canvas canvas) {

        initPaint();
        mPaint.setColor(colorBody);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawRoundRect(bodyRect, radius, radius, mPaint);
    }

    private void drawHands(Canvas canvas) {
        mPaint.setColor(colorBody);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);

        Path path = new Path();
        float hypotenuse = bodyRect.bottom - radius - handsHeight;
        float radiusHand = hypotenuse / 6;
        mPaint.setPathEffect(new CornerPathEffect(radiusHand));

        path.moveTo(bodyRect.left,handsHeight);
        path.lineTo(bodyRect.left - hypotenuse / 2, handsHeight + hypotenuse / 2);
        path.lineTo(bodyRect.left + offset, bodyRect.bottom - radius +offset);
        path.lineTo(bodyRect.left,handsHeight);
        canvas.drawPath(path,mPaint);

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorStroke);
        canvas.drawPath(path, mPaint);

        //        右手
        path.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(colorBody);

        path.moveTo(bodyRect.right, handsHeight);
        path.lineTo(bodyRect.right + hypotenuse / 2, handsHeight + hypotenuse / 2);
        path.lineTo(bodyRect.right  -offset, bodyRect.bottom - radius +offset);
        path.lineTo(bodyRect.right, handsHeight);
        canvas.drawPath(path, mPaint);

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorStroke);
        canvas.drawPath(path, mPaint);

        path.reset();
        mPaint.setStyle(Paint.Style.FILL);
        path.moveTo(bodyRect.left, handsHeight + hypotenuse / 2 - mStrokeWidth);
        path.lineTo(bodyRect.left - mStrokeWidth * 2, handsHeight + hypotenuse / 2 + mStrokeWidth * 2);
        path.lineTo(bodyRect.left, handsHeight + hypotenuse / 2 + mStrokeWidth);
        path.lineTo(bodyRect.left, handsHeight + hypotenuse / 2 - mStrokeWidth);
        canvas.drawPath(path, mPaint);

        path.reset();
        path.moveTo(bodyRect.right, handsHeight + hypotenuse / 2 - mStrokeWidth);
        path.lineTo(bodyRect.right + mStrokeWidth * 2, handsHeight + hypotenuse / 2 + mStrokeWidth * 2);
        path.lineTo(bodyRect.right, handsHeight + hypotenuse / 2 + mStrokeWidth);
        path.lineTo(bodyRect.right, handsHeight + hypotenuse / 2 - mStrokeWidth);
        canvas.drawPath(path, mPaint);

    }


}
