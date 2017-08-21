package com.arrange.event;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/21.
 */

public class EventViewGroup extends FrameLayout implements EventOffsetListener{

    int lastX,lastY;
    EventOffsetListener mEventOffsetListener;

    public EventViewGroup(Context context) {
        super(context);
    }

    public EventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public int getOverScrollMode() {
        return super.getOverScrollMode();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Datafig.e("onTouchEvent ");

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Datafig.e("onTouchEvent  ACTION_DOWN");
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                Datafig.e("onTouchEvent  ACTION_MOVE" + offsetY);

//                if (Math.abs(offsetY) > 20){
                    offsetTopAndBottom(offsetY);
                    return true;
//                }else {
//                }
//                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Datafig.e("dispatchTouchEvent ");
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Datafig.e("dispatchTouchEvent  ACTION_DOWN");
//                lastX = x;
//                lastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - lastX;
//                int offsetY = y - lastY;
//                Datafig.e("dispatchTouchEvent  ACTION_MOVE" + offsetY);
////                if (offsetY < 100){
////                    return true;
////                }
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Datafig.e("onInterceptTouchEvent ");
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        boolean isIntercept = false;
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Datafig.i("onInterceptTouchEvent ACTION_DOWN ");
//
//                lastX = x;
//                lastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - lastX;
//                int offsetY = y - lastY;
//                Datafig.i("onInterceptTouchEvent ACTION_MOVE " + offsetY);
//
//                if (Math.abs(offsetY) > 100){
//                    isIntercept = true;
//                }else {
//                    isIntercept = false;
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void offset(int offset) {
        offsetTopAndBottom(offset);
    }

    @Override
    public boolean intercept() {
        return false;
    }

    public void setEventOffsetListener(EventView eventOffsetListener) {
        this.mEventOffsetListener = eventOffsetListener;
    }
}
