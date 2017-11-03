package com.datastruct.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.fragment.BaseSubFragment;
import com.data.structure.Datafig;
import com.data.structure.graphic.DeepTravel;

/**
 * Created by kan212 on 17/8/7.
 */

public class SortFragment extends BaseSubFragment {

    int[] arr = {49, 38, 65, 97, 76, 13, 27, 50};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BubbleSort sort = new BubbleSort();
//        InsertionSort sort = new InsertionSort();
//        MergeSort sort = new MergeSort();
//        QuickSort sort = new QuickSort();
//        sort.sort(arr);
//        BinaryTree tree = new BinaryTree();
//        tree.Test();
        DeepTravel travel = new DeepTravel();
        travel.travel();
        test();
    }

    private void test() {
        int n = 0;
        try {
            while (true) {
                n++;
                if (n == 10) {
                    Datafig.i("跳出函数 结束");
                    return;
                }
                Datafig.i("跳出函数 n");
            }
        } catch (Exception ev) {

        } finally {
            Datafig.i("finally 的值");
        }

    }


    @Override
    public String getFragmentName() {
        return SortFragment.class.getName();
    }
}
