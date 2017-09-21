package bilibili.fragment;

import android.os.Bundle;

import com.arrange.R;

import bilibili.base.RxLazyFragment;

/**
 * Created by kan212 on 17/9/19.
 */

public class HistoryFragment extends RxLazyFragment {

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
