package com.arrange.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

import com.arrange.R;
import com.arrange.app.MainActivity;
import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/21.
 * 事件处理的demo
 */

public class EventActivity extends Activity{

    EventView evntView;
    EventViewGroup eventViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        evntView = (EventView) findViewById(R.id.evntView);
        eventViewGroup = (EventViewGroup) findViewById(R.id.eventViewGroup);
        evntView.setEventOffsetListener(eventViewGroup);
//        eventViewGroup.setEventOffsetListener(evntView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Datafig.i("dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Datafig.i("onTouchEvent");
        return super.onTouchEvent(event);
    }

    public static void startThisActivity(Activity activity) {
        activity.startActivity(new Intent(activity,EventActivity.class));
    }
}
