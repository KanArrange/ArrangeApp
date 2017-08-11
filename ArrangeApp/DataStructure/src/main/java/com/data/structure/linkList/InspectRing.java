package com.data.structure.linkList;

import com.data.structure.Datafig;

/**
 * Created by kan212 on 17/8/11.
 * 检查单链表是不是有环
 */

public class InspectRing {

    class Node {

        public Node next;
        public int data;

        public Node(int data){
            this.data = data;
        }

        public void append(Node n){
            Node node = this;
            while (null != node.next){
                node = node.next;
            }
            node.next = n;
        }
    }

    public void test(){
        Node node1,node2;

        Node n = new Node(1);
        n.append(new Node(2));
        n.append(new Node(3));
        n.append(new Node(4));
        n.append(new Node(5));
        n.append(new Node(6));
        n.append(new Node(7));
        Node n8 = new Node(8);
        n.append(n8);
        n8.next = n;

        node1 = node2 = n;
        boolean flag = true;
        while (null != n.next){

            if (null != node1.next){
                node1 = node1.next;
            }

            if (null != node2.next.next){
                node2 = node2.next.next;
            }
            if (node2.next.next == null) {
                break;
            }
            if (null != node1 && null != node2 && node1.data == node2.data){
                flag = false;
                break;
            }
        }
        if (flag){
            Datafig.e("not as ring");
        }

    }


}
