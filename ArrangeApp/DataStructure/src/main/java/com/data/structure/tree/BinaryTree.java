package com.data.structure.tree;

import com.data.structure.Datafig;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by kan212 on 17/8/10.
 * 2叉树的遍历
 */

public class BinaryTree {

    public Tree root;

    public BinaryTree(){

    }

    public BinaryTree(Tree tree){
        this.root = tree;
    }

    public static Tree init(){
        Tree a = new Tree('A');

        Tree o = new Tree('O');
        Tree q = new Tree('Q');
        Tree p = new Tree('P',q,null);

        Tree b = new Tree('B', null, a);
        Tree c = new Tree('C');
        Tree d = new Tree('D', b, c);
        Tree e = new Tree('E');
        Tree f = new Tree('F', e, o);
        Tree g = new Tree('G', p, f);
        Tree h = new Tree('H', d, g);

        return h;
    }

    /**
     * 访问节点
     * @param tree
     */
    public static void visit(Tree tree){
        Datafig.e(tree.key);
    }

    /**
     * 递归实现前序遍历
     * @param tree
     */
    public static void preOrder(Tree tree){
        if (null != tree){
            visit(tree);
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    /**
     * 递归实现中序遍历
     * @param tree
     */
    public static void inOrder(Tree tree){
        if (null != tree){
            inOrder(tree.left);
            visit(tree);
            inOrder(tree.right);
        }
    }

    /**
     * 递归实现后续遍历
     * @param tree
     */
    public static void postOrder(Tree tree){
        if (null != tree){
            postOrder(tree.left);
            postOrder(tree.right);
            visit(tree);
        }
    }

    /**
     * 非递归实现前序遍历
     * @param t
     */
    public static void iteractivePreOrder(Tree t){
        Stack<Tree> stack = new Stack<>();
        Tree tree = t;
        while (null != tree || stack.size() > 0){
            while (null != tree){
                visit(tree);
                stack.push(tree);
                tree = tree.left;
            }
            if (stack.size() > 0){
                tree = stack.pop();
                tree = tree.right;
            }
        }
    }

    /**
     * 非递归实现中序遍历
     * @param t
     */
    public static void iteractiveInOrder(Tree t){
        Stack<Tree> stack = new Stack<>();
        Tree tree = t;
        while (null != tree || stack.size() > 0){
            while (null != tree){
                stack.push(tree);
                tree = tree.left;
            }
            if (stack.size() > 0){
                tree = stack.pop();
                visit(tree);
                tree = tree.right;
            }
        }
    }

    public static void iteractivePostOrder(Tree t){
       Stack<Tree> stack = new Stack<>();
       Tree tree = t;
       while (null != t){
           for (;null != t.left ; t = t.left){
               stack.push(t);
           }
           while (null != t && (null == t.right || t.right == tree)){
               visit(t);
               tree = t;
               if (stack.isEmpty()){
                   return;
               }
               t = stack.pop();
           }
           stack.push(t);
           t = t.right;
       }
    }

    /**
     * 递归实现2叉树的反转
     * @param tree
     * @return
     */
    public static Tree Reverse(Tree tree){

        if (null == tree){
            return null;
        }

        Tree temp = tree.right;
        tree.right = tree.left;
        tree.left = temp;

        Reverse(tree.left);
        Reverse(tree.right);
        return tree;
    }


    /**
     * 不使用递归的反转二叉树
     * @param t
     * @return
     */
    public static Tree ReverseNomal(Tree t){

        LinkedList<Tree> list = new LinkedList<>();
        if (null == t){
            return t;
        }
        list.add(t);
        Tree temp ;
        while (list.size() > 0){
            Tree tree = list.removeFirst();
            temp = tree.right;
            tree.right = tree.left;
            tree.left = temp;
            if (null != tree.left) {
                list.add(tree.left);
            }
            if (null != tree.right){
                list.add(tree.right);
            }
        }
        return t;
    }

    /**
     * 层次遍历2叉树
     * @param tree
     */
    public static void levelTraverse(Tree tree){
        LinkedList<Tree> list = new LinkedList<>();
        if (null == tree){
            return;
        }
        list.add(tree);
        while (list.size() > 0){
            Tree temp = list.removeFirst();
            Datafig.e(temp.key);
            if(null != temp.left){
                list.add(temp.left);
            }
            if (null != temp.right){
                list.add(temp.right);
            }
        }
    }


    public static void Test(){
        BinaryTree tree = new BinaryTree(ReverseNomal(init()));
        Datafig.e(" 递归遍历 \n");
        Datafig.e(" Pre-Order:");
        preOrder(tree.root);


        Datafig.e(" in-Order:");
        inOrder(tree.root);

        Datafig.e(" post-Order:");
        postOrder(tree.root);

        Datafig.e("非遍历 post-Order:");
        iteractivePostOrder(tree.root);

        Datafig.e("层次遍历2叉树");
        levelTraverse(tree.root);
    }

}

