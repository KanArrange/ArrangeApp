package com.data.structure.subject;

/**
 * Created by kan212 on 17/8/11.
 * 斐波那契数的计算，递归是最快的方式
 */

public class Fibonacc {

    public int calculate(int i){
        if (0 == i){
            return 0;
        }
        if (1  == i || 2 == i){
            return 1;
        }
        return calculate(i - 1) + calculate(i - 2);
    }

}
