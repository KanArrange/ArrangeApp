package com.arrange.sports.imp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kan212 on 17/9/8.
 */

public class TopMarginBroadcast {

    private List<TopMarginReceiver> receiverList = new ArrayList<TopMarginReceiver>();

    /**
     * 注册TopMargin广播接收器
     *
     * @param receiver
     */
    public void registerTopMarginReceiver(TopMarginReceiver receiver) {
        if (null != receiver && !receiverList.contains(receiver)) {
            receiverList.add(receiver);
        }
    }

    /**
     * 注销TopMargin广播接收器
     *
     * @param receiver
     */
    public void unRegisterTopMarginReceiver(TopMarginReceiver receiver) {
        if (null != receiver && receiverList.contains(receiver)) {
            receiverList.remove(receiver);
        }
    }

    /**
     * 执行广播
     *
     * @param fromePagerId
     * @param toPagerId
     * @param maxMaigin
     * @param precent
     */
    public void broadcast(String fromePagerId, String toPagerId, int maxMaigin, float precent) {
        for (TopMarginReceiver receiver : receiverList) {
            if (null != receiver) {
                receiver.topMarginReceiver(fromePagerId, toPagerId, maxMaigin, precent);
            }
        }
    }

}
