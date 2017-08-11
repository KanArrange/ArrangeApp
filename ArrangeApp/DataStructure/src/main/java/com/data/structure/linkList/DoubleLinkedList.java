package com.data.structure.linkList;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/11.
 * 双向链表
 */

public class DoubleLinkedList {

    public DoubleLinkedList front;
    public DoubleLinkedList last;
    public int val;

    DoubleLinkedList(int v){
        this.val = v;
    }

    public void append(DoubleLinkedList l){
        DoubleLinkedList doubleLinkedList = this;
        while (null != doubleLinkedList.last){
            doubleLinkedList = doubleLinkedList.last;
        }
        doubleLinkedList.last = l;
        l.front = doubleLinkedList;
    }

    public void show(DoubleLinkedList list){
        while (null != list){
            Datafig.e(list.val);
            list = list.last;
        }
    }

    public void test(){
        DoubleLinkedList list = new DoubleLinkedList(10);
        list.append(new DoubleLinkedList(20));
        list.append(new DoubleLinkedList(30));
        list.append(new DoubleLinkedList(40));
        list.append(new DoubleLinkedList(50));
        DoubleLinkedList d = new DoubleLinkedList(60);
        list.append(d);
        list.show(list);
    }

}
