package com.base.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.base.fragment.BaseSubFragment;

/**
 * Created by kan212 on 17/8/7.
 */

public class JumpUtils {

    public static void startSubFragment(Activity activity){
        Fragment fragment = new Fragment();
        if (fragment instanceof BaseSubFragment){
            ((BaseSubFragment) fragment).start(activity);
        }
    }


}
