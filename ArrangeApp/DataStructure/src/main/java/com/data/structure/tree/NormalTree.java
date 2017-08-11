package com.data.structure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kan212 on 17/8/11.
 */

public class NormalTree {

    List<Tree> list = new ArrayList<>();

    class Tree{
        public String val;
        public String parent;
    }

    public void add(String parent,String child){
        Tree tree = new Tree();
        tree.val = child;
        tree.parent = parent;
        list.add(tree);
    }

    public String getParent(String child){
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).val.equals(child)){
                return list.get(i).parent;
            }
        }
        return "";
    }

    public List<String> getChild(String paret){
        List<String> lst = new ArrayList<>();
        for (int i = 0 ;  i< list.size() ; i ++){
            if (list.get(i).parent.equals(paret)){
                lst.add(list.get(i).val);
            }
        }
        return lst;
    }



}
