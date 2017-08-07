package com.data.structure.sort;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/7.
 * 插入排序
 *
 * 从第二个数开始与前一个数两两比较，如果大于前一个数，则继续往下，如果小于前一个数那就与之前的数交换位置
 * 再继续跟之前的之前的数比较直到不小于之前的数为止
 * 时间复杂度： n^2;
 */

public class InsertionSort implements Sort {

    @Override
    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i ++ ){
            int temp = arr[i];

            for (int j = i - 1; j >= 0 && temp < arr[j]; j --){
                arr [j + 1] = arr[j];
                Datafig.e(arr[j + 1]);
                arr[j] = temp;
            }
        }

        for (int i : arr){
            Datafig.e(i);
        }
    }
}
