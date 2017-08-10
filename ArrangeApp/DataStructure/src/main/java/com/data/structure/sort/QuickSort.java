package com.data.structure.sort;

import com.data.structure.Datafig;
import com.data.structure.sort.Sort;

/**
 * Created by kan212 on 17/8/9.
 * nlogn
 */

public class QuickSort implements Sort {

    @Override
    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] list, int low, int high) {
        if (low < high){
            int middle = getMiddle(list,low,high);
            quickSort(list,low,middle - 1);
            quickSort(list,middle + 1,high);
        }
    }

    private int getMiddle(int[] list, int low, int high) {
        int temp = list[low];
        while (low < high){
            while (low < high && list[high] > temp){
                high --;
            }
            list[low] = list[high];
            while (low < high && list[low] < temp){
                low ++;
            }
            list[high] = list[low];
        }
        list[low] = temp;
        Datafig.e(low);
        return low;
    }

}
