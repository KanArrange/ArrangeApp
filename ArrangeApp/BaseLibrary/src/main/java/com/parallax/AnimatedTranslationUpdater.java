package com.parallax;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

/**
 * Created by kan212 on 17/7/21.
 */

public class AnimatedTranslationUpdater implements ParallaxLayerLayout.TranslationUpdater{

    final float maxParallax;
    ParallaxLayerLayout parallax;
    ValueAnimator animation;

    public AnimatedTranslationUpdater() {
        this(1.0f);
    }

    public AnimatedTranslationUpdater(float maxParallax) {
        this.maxParallax = maxParallax;
    }

    @Override
    public void subscribe(ParallaxLayerLayout parallaxLayerLayout) {
        this.parallax = parallaxLayerLayout;

        ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                parallax.updateTranslations(new float[] { (float) animator.getAnimatedValue(), 0.0f });
            }
        };

        animation = ValueAnimator.ofFloat(0.0f, maxParallax, 0.0f, -maxParallax, 0.0f);
        animation.setDuration(1500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.RESTART);
        animation.addUpdateListener(listener);
        animation.start();
    }

    @Override
    public void unSubscribe() {
        if (null != animation && animation.isStarted()){
            animation.end();
        }
    }
}
