package com.base.widget.cprogressbtn;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.view.animation.Animation;

import java.lang.ref.WeakReference;

/**
 * Created by kan212 on 17/6/22.
 */

public class DemoDrawable extends Drawable implements Animatable{


    /**
     * getPadding会将Drawable实例与实例中内容的间隔信息存储在Rect实例中。
     * @param padding
     * @return
     */
    @Override
    public boolean getPadding(Rect padding) {
        return super.getPadding(padding);
    }

    /**
     * setState方法允许客户端告知Drawable实例在什么状态下才进行绘制。
     * 例如“焦点获取状态”，“选中状态”等等。某些Drawable可能会根据选定的状态值变更它们的外观。
     * StateListDrawable
     * @param stateSet
     * @return
     */
    @Override
    public boolean setState(int[] stateSet) {
        return super.setState(stateSet);
    }

    /**
     * 允许客户端提供一个单一的连续控制器来编辑正在显示的Drawable实例，
     * 例如电量水平或者进度值。某些Drawable实例可以根据当前的level值变更它们的外观。
     * @param canvas
     */
//    public boolean setLevel(int level){
//        super.setLevel(level);
//    }

    /**
     * 通过Callback接口，一个Drawable实例可以回调其客户端来执行动画。为了动画可以被执行，
     * 所有的客户端都应该支持这个Callback接口。实现这一效果最简单的方法就是通过系统提供的机制，例如ImageView，View.setBackgoound方法。
     * @param canvas
     */
//    public final void setCallback(Callback cb) {
//        mCallback = new WeakReference<Callback>(cb);
//    }

    /**
     * Bitmap:最简单的Drawable形式，PNG或者JPEG图片。
     *.9图：PNG的一个扩展，可以支持设置其如何填充内容，如何被拉伸。
     * Shape：包含简单的绘制指令，用于替代bitmap，某些情况下对大小调整有更好表现。
     * Layers：一个复合的Drawable，按照层级进行绘制，单个Drawable实例绘制于其下层Drawable实例集合之上。
     * States：一个复合的Drawable，根据它的state选择一个Drawable集合。
     * Levels：一个复合的Drawable，根据它的level选择一个Drawable集合。
     * Scale：一个复合的Drawable和单个Drawable实例构成，它的总体尺寸由它的当前level值决定。
     */
    public void getDrawableInstance(){}


    /**
     * 在通过setBounds设置的范围内进行绘制，通过调用setAlpha和setColorFilter
     * 等方法可以影响绘制的效果。
     *
     * @param canvas 当前Drawable实例要被绘制到canvas上
     */
    @Override
    public void draw(Canvas canvas) {

    }

    /**
     * 为当前Drawable实例设置一个矩形范围，在draw方法调用时候，
     * Drawable实例将被绘制到这个矩形范围内
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }

    /**
     * setBounds方法必须被Drawable实例调用，用于声明Drawable实例绘制的位置和大小。
     * 所有的Drawable实例都会生成请求的尺寸，这一点通常可以通过缩放图像很容易就达到。
     * 对一些Drawable实例，客户端可以通过调用getIntrinsicHeight和getIntrinsicWidth方法得到其首选尺寸。
     * @param bounds
     */
    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
    }

    /**
     * 通过由调用setCallBack设置过的Callback实例执行
     * invalidateDrawable。如果没有调用过setCallback，则无效果
     */
    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
    }

    /**
     * 设置Drawable实例的透明度。
     * 0：完全透明
     * 255:完全不透明
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * 为当前Drawable实例设置颜色滤镜
     * @param colorFilter
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    /**
     * 返回当前Drawable实例的透明或者不透明。返回值是其中之一：
     * {@link android.graphics.PixelFormat#UNKNOWN}-透明度未知
     * {@link android.graphics.PixelFormat#TRANSLUCENT}-半透明
     * {@link android.graphics.PixelFormat#TRANSPARENT}-完全透明
     * {@link android.graphics.PixelFormat#OPAQUE}-完全不透明
     * 如果Drawable中的内容可见性不确定，最安全的方案
     * 是返回TRANSLUCENT/半透明
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 返回当前Drawable实例的实质宽度。
     实质宽度是Drawable实例占据的宽度，包含padding值。
     如果Drawable实例没有实际宽度，例如是一个颜色，则返回-1
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    /**
     * 这个方法很重要，故保留英文注释！
     调用mutate()，使当前Drawable实例mutable，这个操作不可逆。
     一个mutable的Drawable实例不会和其他Drawable实例共享它的状态。
     当你需要修改一个从资源文件加载的Drawable实例时，mutate()方法尤其有用。
     默认情况下，所有加载同一资源文件生成的Drawable实例都共享一个通用的状态，
     如果你修改了其中一个Drawable实例，所有的相关Drawable实例都会发生同样的变化。
     这个方法在[其实你不懂：Drawable着色(tint)的兼容方案 源码解析]
     这篇文章里有过介绍，就是为了限定Drawable实例的编辑生效范围仅限于自身。
     * @return
     */
    @NonNull
    @Override
    public Drawable mutate() {
        return super.mutate();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
