package com.data.structure.linkList;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/10.
 * 倒转单链表
 */

public class ReverseSingleList{

    class Node{

       public int record;
       public Node nextNode;

        public Node(int record){
            super();
            this.record = record;
        }
    }

    /*
     递归，在反转当前节点之前先反转后续节点
     */
    public static Node reverse(Node head){
        if (null == head || null == head.nextNode){
            return head;
        }
        Node reverseHead = reverse(head.nextNode);
        head.nextNode.nextNode = head;
        head.nextNode = null;
        return reverseHead;
    }

    /*
    遍历，将当前节点的下一个节点缓存后更改当前节点指针
     */
    public static Node reverse2(Node head){
        if (null == head){
            return head;
        }
        Node pre = head;
        Node cur = head.nextNode;
        Node next;
        while (null != cur){
            next = cur.nextNode;
            cur.nextNode = pre;
            pre = cur;
            cur = next;
        }
        head.nextNode = null;
        head = pre;
        return head;
    }

    public  void Test(){
        Node head = new Node(0);
        Node tmp = null;
        Node cur = null;
        for (int i = 1 ; i < 10; i ++ ){
            tmp = new Node(i);
            if (1 == i ){
                head.nextNode = tmp;
            }else {
                cur.nextNode = tmp;
            }
            cur = tmp;
        }
        Node h = head;
        while (null != h) {
            h = h.nextNode;
            Datafig.e(h);
        }
    }
}
