package com.base.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.base.R;
import com.base.constant.Constant;

/**
 * Created by kan212 on 17/8/7.
 */

public class SubActivity extends BaseActivity{

    protected Fragment mFragment;
    private FragmentTransaction mTransaction;

    @Override
    public void initView() {
        setContentView(R.layout.activity_sub);
    }

    @Override
    public void initData() {
        String className = getIntent().getStringExtra(Constant.EXTRA_FRAGMENT_TYPE);
        mFragment = getFragment(className);
        mTransaction = this.getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.content_frame, mFragment);
        mTransaction.commitAllowingStateLoss();
    }

    private Fragment getFragment(String className) {
        Fragment fragment = null;
        try {
            Bundle args = getIntent().getExtras();
            fragment = Fragment.instantiate(this, className, args);
        } catch (Exception e) {
            fragment = null;
        }
        return fragment;
    }
}
