package com.data.structure.tree;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/10.
 */

public class BigTree {

    int data;
    BigTree left;
    BigTree right;

    public BigTree(int x){
        data = x;
    }

    public void add(BigTree tree){
        if (tree.data < this.data){
            if (null == left){
                left = tree;
            }else {
                left.add(tree);
            }
        }else {
            if (null == right){
                right = tree;
            }else {
                right.add(tree);
            }
        }
    }

    /**
     * 中序遍历
     */
    public void travel(){
        if (null != left){
            left.travel();
        }
        Datafig.e(data);
        if (null != right){
            right.travel();
        }
        Datafig.e(data);
    }

    public int treeDepth(BigTree tree){
        int tLeft = 0, tRight = 0;
        if (null != tree.left){
            tLeft = treeDepth(tree.left);
        }
        if (null != tree.right){
            tRight = treeDepth(tree.right);
        }
        return (tLeft > tRight) ? (tLeft + 1) : (tRight + 1);
    }

    public void Test(){
        BigTree tree = new BigTree(20);
        tree.add(new BigTree(15));
        tree.add(new BigTree(24));
        tree.add(new BigTree(9));
        tree.add(new BigTree(29));
        tree.add(new BigTree(4));
        tree.add(new BigTree(12));
        tree.travel();
    }

}
