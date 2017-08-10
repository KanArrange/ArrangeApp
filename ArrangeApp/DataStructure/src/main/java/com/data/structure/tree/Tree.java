package com.data.structure.tree;

/**
 * Created by kan212 on 17/8/10.
 */

public class Tree {

    public char key;
    public Tree left;
    public Tree right;

    public Tree(char key){
        this(key,null,null);
    }

    public Tree(char key,Tree left,Tree right){
        this.key = key;
        this.left = left;
        this.right = right;
    }
}
