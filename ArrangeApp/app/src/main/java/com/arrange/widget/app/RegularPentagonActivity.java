package com.arrange.widget.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arrange.R;

/**
 * Created by kan212 on 17/11/1.
 */

public class RegularPentagonActivity extends Activity {

    public static void stratThisActivity(WidgetActivity activity){
        activity.startActivity(new Intent(activity,RegularPentagonActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_regular_pentagon);
    }
}
