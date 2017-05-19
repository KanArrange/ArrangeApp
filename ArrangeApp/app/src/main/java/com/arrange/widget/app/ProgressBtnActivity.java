package com.arrange.widget.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arrange.R;
import com.arrange.widget.widget.progressbtn.ProgressButton;
import com.base.rotate3d.ActivitySwitcher;

/**
 * Created by kan212 on 17/3/8.
 */

public class ProgressBtnActivity extends Activity implements View.OnClickListener{

    private ProgressButton btn_progress;
    private boolean isFromOncreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_btn);
        isFromOncreate = true;
        btn_progress = (ProgressButton) findViewById(R.id.btn_progress);
        btn_progress.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_progress:
                btn_progress.setState(ProgressButton.State.IDLE);
                animatedStartActivity();
                break;
        }
    }

    public static void startThisActivity(WidgetActivity activity) {
        activity.startActivity(new Intent(activity, ProgressBtnActivity.class));
    }

    private void animatedStartActivity() {
        // we only animateOut this activity here.
        // The new activity will animateIn from its onResume() - be sure to implement it.
        final Intent intent = new Intent(getApplicationContext(), CProgressActivity.class);
        // disable default animation for new intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ActivitySwitcher.animationOut(findViewById(R.id.l_container), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        if (isFromOncreate) {
            isFromOncreate = false;
        } else {
//            ActivitySwitcher.animationInReverse(findViewById(R.id.l_container), getWindowManager());
        }
        ActivitySwitcher.animationIn(findViewById(R.id.l_container), getWindowManager());

        super.onResume();
    }

    @Override
    public void finish() {
        // we need to override this to performe the animtationOut on each
        // finish.
//        ActivitySwitcher.animationOut(findViewById(R.id.l_container), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
//            @Override
//            public void onAnimationFinished() {
//                ProgressBtnActivity.super.finish();
//                // disable default animation
//                overridePendingTransition(0, 0);
//            }
//        });
        super.finish();
    }

}
