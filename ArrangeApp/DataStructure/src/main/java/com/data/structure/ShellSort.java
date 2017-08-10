package com.data.structure;

import com.data.structure.sort.Sort;

/**
 * Created by kan212 on 17/8/9.
 * 希尔排序
 */

public class ShellSort implements Sort {

    @Override
    public void sort(int[] arr) {
        int j;
        for (int gap = arr.length / 2; gap > 0; gap /= 2){

            for (int i = gap; i < arr.length; i ++){

                int temp = arr[i];

                for (j = i ; j >= temp && temp < arr[j - gap]; j -= gap){

                    arr[j] = arr[j - gap];

                }

                arr[j] = temp;
            }

        }
        for (int i : arr){
            Datafig.e(i);
        }
    }

}
