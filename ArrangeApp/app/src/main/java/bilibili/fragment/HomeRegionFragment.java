package bilibili.fragment;

import android.os.Bundle;

import com.arrange.R;

import bilibili.base.RxLazyFragment;

/**
 * Created by kan212 on 17/9/19.
 */

public class HomeRegionFragment extends RxLazyFragment {

    public static HomeRegionFragment newInstance() {
        return new HomeRegionFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_live;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
