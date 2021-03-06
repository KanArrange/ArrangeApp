package com.arrange.widget.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.arrange.R;
import com.base.rotate3d.ActivitySwitcher;
import com.base.widget.cprogressbtn.CProgressButton;

/**
 * Created by kan212 on 17/3/9.
 */

public class CProgressActivity extends Activity implements View.OnClickListener{
    public static void startThisActivity(WidgetActivity activity) {
        activity.startActivity(new Intent(activity,CProgressActivity.class));
    }

    private CProgressButton mCProgressButton;
    private TextView tv_plus;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cprogress_btn);
        mCProgressButton = (CProgressButton) findViewById(R.id.btn_cprogress);
        tv_plus = (TextView) findViewById(R.id.tv_plus);
        animation = AnimationUtils.loadAnimation(this, R.anim.plus_add);


        mCProgressButton.setbgDrawable(R.drawable.cprogress_bounder,60);
        mCProgressButton.setStroke(1,R.color.blue);
        mCProgressButton.setOnClickListener(this);
        mCProgressButton.setAnimationEndListener(new CProgressButton.AnimationEndListener() {
            @Override
            public void animationEnd() {
//                iv_rotate.setVisibility(View.VISIBLE);
//                iv_rotate.start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cprogress:
                mCProgressButton.setState(CProgressButton.STATE.PROGRESS);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        tv_plus.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tv_plus.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                tv_plus.startAnimation(animation);

                break;
        }
    }

    @Override
    protected void onResume() {
        ActivitySwitcher.animationIn(findViewById(R.id.r_container), getWindowManager());
        super.onResume();
    }

    @Override
    public void finish() {
        ActivitySwitcher.animationOut(findViewById(R.id.r_container), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
            @Override
            public void onAnimationFinished() {
                CProgressActivity.super.finish();
                // disable default animation
                overridePendingTransition(0, 0);
            }
        });
    }
}
