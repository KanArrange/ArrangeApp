package com.data.structure.linkList;

import com.data.structure.Datafig;

import java.util.LinkedList;

/**
 * Created by kan212 on 17/8/10.
 */

public class SingleLinkList {

    //里面包含数据和指针
    public String data;
    public SingleLinkList next;

    public SingleLinkList(String data){
        this.data = data;
    }
    //添加数据
    public void append(SingleLinkList list){
        SingleLinkList p = this;
        while (p.next != null){
            p = p.next;
        }
        p.next = list;
    }

    //插入数据
    public void add(SingleLinkList list){
        list.next = next;
        next = list;
    }

    public void show(){
        SingleLinkList list = this;
        while (list.next != null){
            Datafig.e(list.data);
            list = list.next;
        }
    }

    public void test(){
        SingleLinkList head = new SingleLinkList("10");
        //向链表中添加几个数据
        head.append(new SingleLinkList("30"));
        head.append(new SingleLinkList("40"));
        head.append(new SingleLinkList("50"));
        //做插入操作
        head.add(new SingleLinkList("20"));
        head.show();
    }


}
