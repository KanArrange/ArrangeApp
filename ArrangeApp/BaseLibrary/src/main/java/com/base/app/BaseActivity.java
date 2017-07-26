package com.base.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by kan212 on 17/7/21.
 */

public abstract class BaseActivity extends FragmentActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public abstract void initView();

    public abstract void initData();

}
