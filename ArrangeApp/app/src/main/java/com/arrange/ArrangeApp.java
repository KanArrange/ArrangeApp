package com.arrange;

import android.app.Application;
import android.content.Context;

/**
 * Created by kan212 on 17/3/27.
 */

public class ArrangeApp extends Application{

    public static Context mCotext;

    @Override
    public void onCreate() {
        super.onCreate();
        mCotext = this;
    }
}
