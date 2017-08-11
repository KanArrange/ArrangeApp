package com.data.structure.graphic;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/11.
 */

public class DeepTravel {

    public void travel(){

        int[][] a = {
                { 0, 1, 1, 1, 0 },
                { 1, 0, 1, 1, 1 },
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 0, 1, 0, 0, 0 },
        };
        int[] color = new int[a.length];
        //0:从零这个节点开始遍历
        deepTravel(a,color,0);
    }

    private void deepTravel(int[][] a, int[] color, int i) {
        color[i] = 1;
        Datafig.e(i);
        for (int j = 0; j < a[i].length; j ++ ){
            if (a[i][j] == 1 && color[j] == 0 ){
                Datafig.e("数据: " + a[i][j]);
                deepTravel(a,color,j);
            }
        }
    }

}
