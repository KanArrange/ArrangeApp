package com.data.structure.sort;

/**
 * Created by kan212 on 17/8/7.
 * 堆排序
 */

public class HeapSort implements Sort {

    @Override
    public void sort(int[] arr) {
        heapSort(arr);
    }

    public void heapSort(int[] arr) {
        // 建立初始堆,从最后一个非叶子节点开始比较（arr.length-1）/2
        for (int i = (arr.length - 1) / 2; i >= 0; i--)
            heapOne(arr, arr.length, i);
        // 边输出堆顶边调整
        int n = arr.length;
        while (n > 0) {
            System.out.print(arr[0] + " ");// 输出堆顶元素
            arr[0] = arr[n - 1];// 最后一个元素移动到堆顶
            n--;
            heapOne(arr, n, 0);
        }
    }

    // 节点K进行筛选
    // a:堆数据，n:堆中有效数据个数 k:待筛选节点
    public void heapOne(int[] arr, int n, int k) {
        // n:字符串长度
        // k:当前节点
        int k1 = 2 * k + 1;// 左孩子计算公式
        int k2 = 2 * k + 2;// 右孩子计算公式
        if (k1 >= n && k2 >= n)
            return; // 已经是叶子了

        int a1 = Integer.MAX_VALUE;
        int a2 = Integer.MAX_VALUE;

        if (k1 < n)
            a1 = arr[k1];// 左孩子
        if (k2 < n)
            a2 = arr[k2];// 右孩子

        if (arr[k] <= a1 && arr[k] <= a2)
            return;// 已经符合堆的要求

        // 找到左右孩子中最小的，和它交换
        if (a1 < a2) {
            int t = arr[k];
            arr[k] = arr[k1];
            arr[k1] = t;
            heapOne(arr, n, k1);// 继续筛选子树
        } else {
            int t = arr[k];
            arr[k] = arr[k2];
            arr[k2] = t;
            heapOne(arr, n, k2);// 继续筛选子树
        }
    }
}
