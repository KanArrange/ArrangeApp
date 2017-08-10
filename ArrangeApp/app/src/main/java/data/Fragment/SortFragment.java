package data.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.fragment.BaseSubFragment;
import com.data.structure.sort.QuickSort;
import com.data.structure.tree.BinaryTree;

/**
 * Created by kan212 on 17/8/7.
 */

public class SortFragment extends BaseSubFragment{

    int[] arr = { 49, 38, 65, 97, 76, 13, 27, 50 };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BubbleSort sort = new BubbleSort();
//        InsertionSort sort = new InsertionSort();
//        MergeSort sort = new MergeSort();
//        QuickSort sort = new QuickSort();
//        sort.sort(arr);
        BinaryTree tree = new BinaryTree();
        tree.Test();
    }

    @Override
    public String getFragmentName() {
        return SortFragment.class.getName();
    }
}
