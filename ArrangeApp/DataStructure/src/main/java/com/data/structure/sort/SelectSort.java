package com.data.structure.sort;

/**
 * Created by kan212 on 17/8/9.
 * 选择排序 n * (n -1)
 * 时间复杂度 n ^ 2
 */

public class SelectSort implements Sort {

    @Override
    public void sort(int[] arr) {
        for (int i = 0 ;i <arr.length - 1; i++){

            int minIndex = i;

            for (int j = i + 1; j < arr.length; j ++){

                if (arr[j] < arr[minIndex]){
                    minIndex = j;
                }

            }

            if (minIndex != i){
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

}
