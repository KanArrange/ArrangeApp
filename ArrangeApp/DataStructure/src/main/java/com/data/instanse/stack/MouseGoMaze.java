package com.data.instanse.stack;

/**
 * Created by kan212 on 17/8/23.
 */

public class MouseGoMaze {

    int[][] maze = {
            {2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 0, 2, 0, 2},
            {2, 0, 0, 0, 0, 2, 2},
            {2, 2, 0, 2, 0, 2, 2},
            {2, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2}};

    int outX = 5;
    int outY = 6;

    class Link {
        public int x, y;
        public Link next;

    }

    public Link push(Link l,int x, int y){
        Link link = l;
        link.x = x;
        link.y = y;
        link.next = l;
        l = link;
        return l;
    }

    public Link pop(Link l, int x, int y){
        Link top = null;
        if(null != l){
            top = l;
            l = l.next;
            top.x = x;
            top.y = y;
        }
        return l;
    }

    public void maze(){
        int i, j, x, y;
        Link path = null;
        x = 2;
        y = 2;
        while (x <= outX && y<= outY){
            maze[x][y] = 3;
            if (maze[x -1][y] == 0){
                x = x -1;
                path = push(path,x,y);
            }else if (maze[x + 1][y] == 0){
                x = x + 1;
                path = push(path,x,y);
            }else if(maze[x][y - 1] == 0){
                y = y - 1;
                path = push(path,x,y);
            }else if(maze[x][y + 1] == 0){
                y = y +1;
                path = push(path,x,y);
            }else if (check(x,y)){
                break;
            }
            else {
                maze[x][y] = 4;
                path = pop(path,x,y);
            }
        }
    }

    private boolean check(int x, int y) {
        if (x == outX && y == outY){
            return  true;
        }else {
            return false;
        }
    }


}
