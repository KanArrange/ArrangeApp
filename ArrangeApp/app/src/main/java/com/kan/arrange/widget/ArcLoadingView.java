package com.kan.arrange.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.kan.arrange.R;

/**
 * Created by hongtao on 16/3/21.
 */
public class ArcLoadingView extends View {

    private Paint paint;
    private float angle = 0.0f;
    private final int RADIUS = 105;
    private ObjectAnimator rotateAnim;
    private ObjectAnimator arcAnim;


    public ArcLoadingView(Context context) {
        super(context);
        initView();
    }

    public ArcLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ArcLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        initAnim();
        rotateAnim.start();
        arcAnim.start();
    }

    private void initAnim() {

        if (null == rotateAnim) {
            rotateAnim = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setDuration(2000);
        }

        if (null == arcAnim) {
            arcAnim = ObjectAnimator.ofFloat(this, "angle", 150, 0);
            arcAnim.setRepeatMode(Animation.REVERSE);
            arcAnim.setRepeatCount(Animation.INFINITE);
            arcAnim.setDuration(2000);
        }
    }

    public void setAngle(float angle) {
        float arc = (float) (angle * Math.PI * RADIUS / 180);
        if (arc > 150.0f) {
            arc = 150.0f;
        }
        this.angle = arc;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int viewWidth = modeWidth == MeasureSpec.EXACTLY ? sizeWidth : RADIUS * 2;
        int viewHeight = modeHeight == MeasureSpec.EXACTLY ? sizeHeight : RADIUS * 2;

        this.setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF oval = new RectF(10, 10, 200, 200);
        canvas.drawArc(oval, 0, angle, false, paint);
        canvas.drawArc(oval, 180, angle, false, paint);
        Bitmap bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
        canvas.drawBitmap(bitmap,null,oval,paint);
    }
}
