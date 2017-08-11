package com.data.structure.graphic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kan212 on 17/8/11.
 * 图的广度搜素
 *
 */

public class WideTravel {

    public void travel(){

        int[][] graphic = {
                { 0, 1, 1, 0, 0, 0, 0 },
                { 1, 0, 0, 1, 0, 0, 1 },
                { 1, 0, 0, 0, 0, 1, 1 },
                { 0, 1, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 1, 1 },
                { 0, 0, 1, 0, 1, 0, 0 },
                { 0, 1, 1, 0, 1, 0, 0 }
        };
        List list = new ArrayList();
        Set set = new HashSet<>();//已经遍历节点的集合
        list.add(0);
        while (true){
            if (list.isEmpty()){
                break;
            }
            int node = (int) list.get(0);
            set.add(node);
            list.remove(node);
            for (int i = 0;i < graphic[node].length;i ++ ){
                if (graphic[node][i] == 1 && !set.contains(i) && list.indexOf(i) < 0){
                    list.add(i);
                }
            }
        }
    }

}
