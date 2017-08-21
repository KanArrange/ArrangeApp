package com.arrange.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/21.
 */

public class EventView extends View implements EventOffsetListener{

    int lastX;
    int lastY;
    int disLastX,disLastY;

    private EventOffsetListener mEventOffsetListener;

    public void setEventOffsetListener(EventOffsetListener mEventOffsetListener) {
        this.mEventOffsetListener = mEventOffsetListener;
    }

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        Datafig.e("dispatchTouchEvent ");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Datafig.e("dispatchTouchEvent ACTION_DOWN");
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - lastX;
//                int offsetY = y - lastY;
//                Datafig.e("dispatchTouchEvent ACTION_MOVE" + offsetY);
//                if (Math.abs(offsetY) > 100){
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }else {
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Datafig.e("onTouchEvent ");

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Datafig.i("onTouchEvent ACTION_DOWN ");
                disLastX = x;
                disLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int offsetX = x - disLastX;
                int offsetY = y - disLastY;
                Datafig.i("onTouchEvent ACTION_MOVE " + offsetY);

                if (Math.abs(offsetY) > 20){
                    mEventOffsetListener.offset(offsetY);
                    Datafig.i("onTouchEvent ACTION_DOWN return false");
                    return false;
                }else {
                    offsetTopAndBottom(offsetY);
                    return true;
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void offset(int offset) {
        offsetTopAndBottom(offset);
    }

    @Override
    public boolean intercept() {
        return false;
    }
}
