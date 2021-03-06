package com.arrange.widget.widget.sports;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by kan212 on 17/11/1.
 * 正五边形view
 */

public class RegularPentagonView extends View {


    private int count = 5;                //数据个数
    private float angle = (float) (Math.PI * 2 / count);    //五边形角度
    private float radius;                   //网格最大半径
    private int centerX;                  //中心X
    private int centerY;                  //中心Y
    private double[] data = {2, 3, 5, 4, 1}; //各维度分值
    private float maxValue = 5;             //数据最大值
    private Paint mainPaint;
    //网格区画笔
    private Paint valuePaint;               //数据区画笔

    private Paint textPaint;               //文字区画笔

    public RegularPentagonView(Context context) {
        super(context);
    }

    public RegularPentagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainPaint = new Paint();
        mainPaint.setColor(Color.parseColor("#b5b5b5"));
        mainPaint.setStrokeWidth(3);
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);


        valuePaint = new Paint();
        valuePaint.setColor(Color.parseColor("#f39700"));
        valuePaint.setStrokeWidth(3);
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        valuePaint.setShadowLayer(4,2,2,Color.RED);


        textPaint = new Paint();
        textPaint.setTextSize(36);
        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);

    }

    public RegularPentagonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 4 * 0.9f;
        //中心坐标
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
        drawText(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i <= count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.moveTo(x, y);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x1 = (float) (centerX + curR * Math.sin(angle / 2));
                    float y1 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x1, y1);
                    float x2 = (float) (centerX - curR * Math.sin(angle / 2));
                    float y2 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x2, y2);
                    float x3 = (float) (centerX - curR * Math.sin(angle));
                    float y3 = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x3, y3);
                    float x4 = (float) (centerX);
                    float y4 = (float) (centerY - curR);
                    path.lineTo(x4, y4);
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x, y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        path.reset();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        float curR = r * 5;//当前半径
        float x = (float) (centerX + curR * Math.sin(angle));
        float y = (float) (centerY - curR * Math.cos(angle));
        path.moveTo(x, y);
        path.lineTo(centerX, centerY);
        float x1 = (float) (centerX + curR * Math.sin(angle / 2));
        float y1 = (float) (centerY + curR * Math.cos(angle / 2));
        path.moveTo(x1, y1);
        path.lineTo(centerX, centerY);
        float x2 = (float) (centerX - curR * Math.sin(angle / 2));
        float y2 = (float) (centerY + curR * Math.cos(angle / 2));
        path.moveTo(x2, y2);
        path.lineTo(centerX, centerY);
        float x3 = (float) (centerX - curR * Math.sin(angle));
        float y3 = (float) (centerY - curR * Math.cos(angle));
        path.moveTo(x3, y3);
        path.lineTo(centerX, centerY);
        float x4 = (float) (centerX);
        float y4 = (float) (centerY - curR);
        path.moveTo(x4, y4);
        path.lineTo(centerX, centerY);
        path.close();
        canvas.drawPath(path, mainPaint);
    }

    private void drawText(Canvas canvas){
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        float curR = r * 6f;//当前半径
        float x = (float) (centerX + curR * Math.sin(angle));
        float y = (float) (centerY - curR * Math.cos(angle));
        float tw = textPaint.measureText("得分");
        float tx = x - tw / 2;
        float ty = y + textPaint.getFontMetricsInt().bottom * 2;
        canvas.drawCircle(tx, ty - 5, 5, valuePaint);
        canvas.drawText("得分",tx,ty,textPaint);
    }



    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        double percent1;
        if (data[0] != maxValue) {
            percent1 = data[0] % maxValue;
        } else {
            percent1 = maxValue;
        }
        float x1 = (float) (centerX + r * percent1 * Math.sin(angle));
        float y1 = (float) (centerY - r * percent1 * Math.cos(angle));
        //绘制小圆点
        path.moveTo(x1, y1);
        canvas.drawCircle(x1, y1, 5, valuePaint);
        double percent2;
        if (data[1] != maxValue) {
            percent2 = data[1] % maxValue;
        } else {
            percent2 = maxValue;
        }
        float x2 = (float) (centerX + r * percent2 * Math.sin(angle / 2));
        float y2 = (float) (centerY + r * percent2 * Math.cos(angle / 2));
        //绘制小圆点
        path.lineTo(x2, y2);
        canvas.drawCircle(x2, y2, 5, valuePaint);
        double percent3;
        if (data[2] != maxValue) {
            percent3 = data[2] % maxValue;
        } else {
            percent3 = maxValue;
        }
        float x3 = (float) (centerX - r * percent3 * Math.sin(angle / 2));
        float y3 = (float) (centerY + r * percent3 * Math.cos(angle / 2));
        //绘制小圆点
        path.lineTo(x3, y3);
        canvas.drawCircle(x3, y3, 5, valuePaint);
        double percent4;
        if (data[3] != maxValue) {
            percent4 = data[3] % maxValue;
        } else {
            percent4 = maxValue;
        }
        float x4 = (float) (centerX - r * percent4 * Math.sin(angle));
        float y4 = (float) (centerY - r * percent4 * Math.cos(angle));
        //绘制小圆点
        path.lineTo(x4, y4);
        canvas.drawCircle(x4, y4, 5, valuePaint);
        double percent5;
        if (data[4] != maxValue) {
            percent5 = data[4] % maxValue;
        } else {
            percent5 = maxValue;
        }
        float x5 = (float) (centerX);
        float y5 = (float) (centerY - r * percent5);
        //绘制小圆点
        path.lineTo(x5, y5);
        canvas.drawCircle(x5, y5, 5, valuePaint);
        path.lineTo(x1, y1);
        path.close();
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(90);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

}
