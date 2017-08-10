package com.data.structure.search;

/**
 * Created by kan212 on 17/8/10.
 * 2分查找
 *
 * log 2 (n+1)，且 期望时间复杂度为O(log 2 n)
 */

public class HalfSearch {

    // 非递归实现
    public static int binary(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low < high){
            int mid = (low + high) / 2;
            if (value == array[mid]){
                return mid;
            }
            if (value > array[mid]){
                low = mid + 1;
            }
            if (value < array[mid]){
                high = mid - 1;
            }
        }
        return -1;
    }

    //递归实现
    public static int BinSearch(int[] array, int key, int low, int high) {
        if (low < high){
            int mid = (low + high) / 2;
            if (key == array[mid]){
                return mid;
            }
            if (key < array[mid]){
                return  BinSearch(array,key,low,mid - 1);
            }
            if (key > array[mid]){
                return BinSearch(array,key,mid + 1,high);
            }
        }
        return 0;
    }
}