package com.data.structure.linkList;

import com.data.structure.Datafig;

import java.util.LinkedList;

/**
 * Created by kan212 on 17/8/11.
 * 链表的增删改查
 */

public class Operations {

    public static void add(SingleLinkList list, SingleLinkList x){
        x.next = list.next;
        list.next = x;
    }

    public static void delete(SingleLinkList list,SingleLinkList x){
        list.next = x.next;
    }

    public static void show(SingleLinkList list){
        SingleLinkList p = list;
        while (null != p.next){
            Datafig.e(p.data);
            p = p.next;
        }
    }
}

