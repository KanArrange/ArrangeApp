package com.arrange.sports.imp;

/**
 * Created by kan212 on 17/9/8.
 */

public interface TopMarginReceiver {
    /**
     * topMargin接收器
     *
     * @param fromPagerId curr显示页Id
     * @param toPagerId   next显示页Id
     * @param maxMaigin   滑动最大区间Margin
     * @param precent     当前margin所占比例
     */
    public void topMarginReceiver(String fromPagerId, String toPagerId, int maxMaigin, float precent);
}
