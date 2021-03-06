/**
 * Project Name:CustomAndroid
 * File Name:DensityUtil.java
 * Package Name:custom.android.util
 * Date:2014-12-1下午4:50:40
 * Copyright (c) 2014, boqing@staff.sina.com.cn All Rights Reserved.
 *
 */

package com.base.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * ClassName:DensityUtil <br/>
 * Function: 手机分辨率的转换 <br/>
 * Date:     2014-12-1 下午4:50:40 <br/>
 *
 * @author yangboqing
 * @see
 * @since JDK 1.6
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Resources resouces, float dpValue) {
        final float scale = resouces.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
