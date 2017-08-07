package com.data.structure.sort;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/7.
 * 冒泡排序算法
 */

public class BubbleSort implements Sort {

    @Override
    public void sort(int[] arr) {

        for (int i = 1; i < arr.length ; i ++ ){

            for (int j = 0; j < arr.length - i ; j ++ ){

                if (arr[j] > arr[j + 1]){
                    int temp =  arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

        }

        for (int i = 0; i < arr.length ; i ++ ){
            Datafig.e(arr[i]);
        }
    }

}
