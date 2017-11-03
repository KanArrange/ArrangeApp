package com.base.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.base.app.SubActivity;
import com.base.constant.Constant;

/**
 * Created by kan212 on 17/8/7.
 */

public abstract class BaseSubFragment extends Fragment{

    public View mView;

    public BaseSubFragment(){
    }

    public void start(Activity activity){
        activity.startActivity(getBaseIntent(activity));
    }

    public Intent getBaseIntent(Activity activity){
        Intent intent = new Intent(activity, SubActivity.class);
        intent.putExtra(Constant.EXTRA_FRAGMENT_TYPE,getFragmentName());
        return intent;
    }

    public abstract String getFragmentName();
}
