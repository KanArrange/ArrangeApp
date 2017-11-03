package bilibili.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bilibili.utils.ConstantUtil;

import bilibili.base.RxBaseActivity;

/**
 * Created by kan212 on 17/9/19.
 */

public class TotalStationSearchActivity extends RxBaseActivity {
    
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    public static void launch(FragmentActivity activity, String str) {
        Intent mIntent = new Intent(activity, TotalStationSearchActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CONTENT, str);
        activity.startActivity(mIntent);
    }
}
