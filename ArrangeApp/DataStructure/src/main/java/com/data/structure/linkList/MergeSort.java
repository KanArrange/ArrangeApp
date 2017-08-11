package com.data.structure.linkList;

/**
 * Created by kan212 on 17/8/11.
 * 链表的归并排序
 */

public class MergeSort {

    class LinkNode{

        public int data;
        public LinkNode next;

        LinkNode(int val){
            this.data = val;
            next = null;
        }
    }

    public LinkNode sortList(LinkNode linkNode){
        if (null == linkNode || null == linkNode.next){
            return linkNode;
        }
        LinkNode cur = linkNode;
        LinkNode post = linkNode.next;
        while (null != post.next && null != post.next.next){
            cur = cur.next;
            post = post.next.next;
        }
        LinkNode linkNode1 = sortList(cur.next);
        cur.next = null;
        return  merge(sortList(linkNode),linkNode1);
    }

    public LinkNode merge(LinkNode h1, LinkNode h2) {

        LinkNode hn = new LinkNode(-1);
        LinkNode c = hn;

        while (h1 != null && h2 != null) {
            if (h1.data <= h2.data) {
                c.next = h1;
                h1 = h1.next;
            } else {
                c.next = h2;
                h2 = h2.next;
            }
            c = c.next;
        }

        if (h1 == null) {
            c.next = h2;
        } else {
            c.next = h1;
        }

        return hn.next;

    }

}
