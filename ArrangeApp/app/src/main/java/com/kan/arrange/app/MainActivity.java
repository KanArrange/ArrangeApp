package com.kan.arrange.app;

import android.app.Activity;
import android.os.Bundle;

import com.kan.arrange.R;
import com.kan.arrange.notification.NotificationHandler;
import com.kan.arrange.widget.WhorlView;

public class MainActivity extends Activity {

    private WhorlView mWhorlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationHandler.getInstance(this).createProgressNotification(this);
        NotificationHandler.getInstance(this).createButtonNotification(this);
        NotificationHandler.getInstance(this).createExpandableNotification(this);
        NotificationHandler.getInstance(this).createSimpleNotification(this);
    }
}
