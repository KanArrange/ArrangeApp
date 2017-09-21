package com.arrange;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.kan.database.greendao.gen.DaoMaster;


/**
 * Created by kan212 on 17/3/27.
 */

public class ArrangeApp extends Application{


    public static Context mCotext;
    public static ArrangeApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mCotext = this;
        mInstance = this;
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mCotext,"test");
    }

    public static ArrangeApp getInstance() {
        return mInstance;
    }
}
