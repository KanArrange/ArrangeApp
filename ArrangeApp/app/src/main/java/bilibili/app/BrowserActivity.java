package bilibili.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bilibili.utils.ConstantUtil;

import bilibili.base.RxBaseActivity;


/**
 * Created by kan212 on 17/9/20.
 */

public class BrowserActivity extends RxBaseActivity {

    public static void launch(Activity activity, String url, String title) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra(ConstantUtil.EXTRA_URL, url);
        intent.putExtra(ConstantUtil.EXTRA_TITLE, title);
        activity.startActivity(intent);
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
