package bilibili.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bilibili.utils.ConstantUtil;

import bilibili.base.RxBaseActivity;

/**
 * Created by kan212 on 17/9/22.
 */

public class VideoDetailsActivity extends RxBaseActivity {

    public static void launch(Activity activity, int aid, String imgUrl) {
        Intent intent = new Intent(activity, VideoDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstantUtil.EXTRA_AV, aid);
        intent.putExtra(ConstantUtil.EXTRA_IMG_URL, imgUrl);
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
