package com.arrange.widget.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arrange.R;
import com.arrange.widget.widget.OvalProgress;

/**
 * Created by kan212 on 17/3/13.
 */

public class OvalProgressActivity extends Activity{
    public static void stratThisActivity(WidgetActivity activity) {
        activity.startActivity(new Intent(activity,OvalProgressActivity.class));
    }

    private OvalProgress oval_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_oval);
        oval_progress = (OvalProgress) findViewById(R.id.oval_progress);
        oval_progress.setProgress(84);
    }
}
