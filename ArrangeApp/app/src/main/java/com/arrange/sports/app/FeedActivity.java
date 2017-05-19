package com.arrange.sports.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import com.arrange.app.MainActivity;

/**
 * Created by kan212 on 17/3/24.
 */

public class FeedActivity extends FragmentActivity{

    public static void startThisActivity(MainActivity activity) {
        activity.startActivity(new Intent(activity,FeedActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


}
