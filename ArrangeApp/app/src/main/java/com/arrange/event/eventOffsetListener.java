package com.arrange.event;

/**
 * Created by kan212 on 17/8/21.
 */

public interface EventOffsetListener {

    void offset(int offset);

    boolean intercept();
}
