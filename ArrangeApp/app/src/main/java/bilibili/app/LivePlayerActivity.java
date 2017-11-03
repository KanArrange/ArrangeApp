package bilibili.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bilibili.utils.ConstantUtil;

import bilibili.base.RxBaseActivity;

/**
 * Created by kan212 on 17/9/20.
 */

public class LivePlayerActivity extends RxBaseActivity {

    public static void launch(Activity activity, int cid, String title, int online, String face, String name, int mid) {
        Intent mIntent = new Intent(activity, LivePlayerActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CID, cid);
        mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title);
        mIntent.putExtra(ConstantUtil.EXTRA_ONLINE, online);
        mIntent.putExtra(ConstantUtil.EXTRA_FACE, face);
        mIntent.putExtra(ConstantUtil.EXTRA_NAME, name);
        mIntent.putExtra(ConstantUtil.EXTRA_MID, mid);
        activity.startActivity(mIntent);
    }

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
}
