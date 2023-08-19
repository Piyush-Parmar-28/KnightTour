package Knight_Tour;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Tour {
    static class pair{
        int x;
        int y;
        int prevX;
        int prevY;
        int steps;
        String dir;

        pair(int x, int y, int steps, String dir, int prevX, int prevY){
            this.x= x;
            this.y= y;
            this.steps= steps;
            this.dir= dir;
            this.prevX= prevX;
            this.prevY= prevY;
        }
    }

    static int n;
    static JLabel[][] jLabel;
    static int[][] board;
    static boolean[][] vis;

    static class myFrame extends JFrame{
        myFrame(int boardSize){
            n= boardSize;

            jLabel = new JLabel[n][n];
            board= new int[n][n];
            vis= new boolean[n][n];

            setVisible(true);
            setSize(500,500);

            setLayout(new GridLayout(n, n, 2, 2));
            setTitle("Knight Tour Visualization");
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    jLabel[i][j] = new JLabel();
                    jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                    jLabel[i][j].setOpaque(true);
                    jLabel[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                    jLabel[i][j].setForeground(Color.WHITE);
                    jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);

                    add(jLabel[i][j]);
                }
            }
        }
    }

    public static void main( String args[] ){
        myFrame obj= new myFrame( 6 );

        jLabel[0][0].setBackground(Color.GREEN);
        jLabel[3][4].setBackground(Color.ORANGE);

        int[] start= {3, 4};
        int[] dest= {0, 0};

        int steps= getSteps(dest[0], dest[1], start[0], start[1]);
        System.out.println("Min steps to reach destination is: "+ steps);
    }

    public static int getSteps(int xDest, int yDest, int x, int y){
        Queue<pair> q1= new LinkedList<>();
        q1.add(new pair(x, y, 0, "", x, y));
        vis[x][y]= true;

        int[] xDir= {-2,-2,-1,-1,1,1,2,2};
        int[] yDir= {-1,1,-2,2,-2,2,-1,1};
        String[] dir= {"up", "up", "left", "right", "left", "right", "down", "down"};

        while(q1.size() > 0){
            wait(2000);

            //  Color
            Random rand= new Random();
            float r= rand.nextFloat();;
            float g= rand.nextFloat();;
            float b= rand.nextFloat();;
            Color obj= new Color(r, g, b);

            pair p= q1.poll();

            //  Coloring the path
            if(p.dir.equals("up") || p.dir.equals("down")){
                //  Highlighting the border
                highlightPathUpDown(p.prevX, p.prevY, p.x, p.y, p.steps, obj);

                wait(2000);

                //  Coloring the path
                colorPathUpDown(p.prevX, p.prevY, p.x, p.y, p.steps, obj);
            }
            else{
                //  Highlighting the border
                highlightPathLeftRight(p.prevX, p.prevY, p.x, p.y, p.steps, obj);

                wait(2000);

                //  Coloring the path
                colorPathLeftRight(p.prevX, p.prevY, p.x, p.y, p.steps, obj);
            }

            if(p.x == xDest && p.y == yDest){
                return p.steps;
            }

            for(int i=0; i<8; i++){
                if( check(n, p.x+xDir[i], p.y+yDir[i]) ){
                    vis[p.x+xDir[i]][p.y+yDir[i]]= true;
                    q1.add(new pair(p.x+xDir[i], p.y+yDir[i], p.steps+1, dir[i], p.x, p.y));
                }
            }
        }

        return -1;
    }

    public static boolean check(int n, int x, int y){
        if(x<0 || x>=n || y<0 || y>=n || vis[x][y]){
            return false;
        }
        return true;
    }

    public static void highlightPathUpDown(int prevX, int prevY, int x, int y, int steps, Color obj){
        for(int i= Math.min(prevX, x); i<= Math.max(prevX, x); i++){
            jLabel[i][prevY].setBorder(BorderFactory.createLineBorder(obj, 3));
        }

        for(int i= Math.min(prevY, y); i<= Math.max(prevY, y); i++){
            jLabel[x][i].setBorder(BorderFactory.createLineBorder(obj, 3));
        }
    }

    public static void highlightPathLeftRight(int prevX, int prevY, int x, int y, int steps, Color obj){
        for(int i= Math.min(prevY, y); i<= Math.max(prevY, y); i++){
            jLabel[prevX][i].setBorder(BorderFactory.createLineBorder(obj, 3));
        }

        for(int i= Math.min(prevX, x); i<= Math.max(prevX, x); i++){
            jLabel[i][y].setBorder(BorderFactory.createLineBorder(obj, 3));
        }
    }

    public static void colorPathUpDown(int prevX, int prevY, int x, int y, int steps, Color obj){
        for(int i= Math.min(prevX, x); i<= Math.max(prevX, x); i++){
            jLabel[i][prevY].setBackground(obj);
            jLabel[i][prevY].setText(steps+"");
        }

        for(int i= Math.min(prevY, y); i<= Math.max(prevY, y); i++){
            jLabel[x][i].setBackground(obj);
            jLabel[x][i].setText(steps+"");
        }
    }

    public static void colorPathLeftRight(int prevX, int prevY, int x, int y, int steps, Color obj){
        for(int i= Math.min(prevY, y); i<= Math.max(prevY, y); i++){
            jLabel[prevX][i].setBackground(obj);
            jLabel[prevX][i].setText(steps+"");
        }

        for(int i= Math.min(prevX, x); i<= Math.max(prevX, x); i++){
            jLabel[i][y].setBackground(obj);
            jLabel[i][y].setText(steps+"");
        }
    }

    public static void wait(int time_ms){
        try {
            Thread.sleep(time_ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}