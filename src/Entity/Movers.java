package Entity;

import Animation.Texture;
import Game.Game;

import java.io.IOException;
import java.util.Random;


public class Movers extends Entity{
    double moveDxx;
    double moveDyy;
    protected Random rand = new Random(System.nanoTime());
    public double speed;//higher number means slower speed, 60 is 1 tile per second

    public Movers(double xpos, double ypos, Texture texture, double moveDxx, double moveDyy) {
        super(xpos, ypos, texture);
        this.moveDxx = moveDxx;
        this.moveDyy = moveDyy;

        this.speed=6;//higher number means slower speed, 60 is 1 tile per frame? second?
    }






    /**
     * *AI function*
     *Has Random movment of Sprite
     *
     */
    protected void randomMovement() {
        int switchNum = rand.nextInt(6);
        switch(switchNum){

            case 0:
                moveDxx = 0.1;
                moveDyy = 0.1;
                break;
            case 1:
                moveDxx = -0.1;
                moveDyy = 0.1;
                break;
            case 2:
                moveDxx = 0.1;
                moveDyy = -0.1;
                break;
            case 3:
                moveDxx = -0.2;
                moveDyy = 0.2;
                break;
            case 4:
                moveDxx = 0;
                moveDyy = 0.1;
                break;
            case 5:
                moveDxx = 0.1;
                moveDyy = 0;
                break;
        }
    }

    protected boolean collision(double x, double y){
//        System.out.println("X:"+ x);
//        System.out.println("Y:"+ y);
//        System.out.println("X:(int)"+ (int)x);
//        System.out.println("Y:(int)"+(int) y);
//        System.out.println("X int math round)"+(int) Math.round(x));
//        System.out.println("Y int math round)"+(int) Math.round(y));
//        x=(int) Math.round(x);
//        y=(int) Math.round(y);
//
//        if( (Game.Game.map[(int) x][(int) y] > 0)  ){
//
//            return true;
//        }else{
//            return false;
//        }
        return Game.level.collision((int) x, (int) y);

    }




//    protected void persue(double delta) {
//
//        if (Game.camera.xPos > this.xpos) { //System.out.println("Line 88 MoveDx:" + moveDx);
//            moveDx = Math.round(1 / speed);
//
//            //moveDx=moveDx*delta;
//            // System.out.println("Line 90 MoveDx:" + moveDx);
//        }else if (Game.camera.xPos < this.xpos) {
//            moveDx = - Math.round((1 / speed));
//            // moveDx=moveDx*delta;
//            //System.out.println("Line 93 MoveDx:" + moveDx);
//        }else
//            moveDx = 0;
//        if (Game.camera.yPos > this.ypos) {
//            moveDy = Math.round(1 / speed);
//            // moveDy=moveDy*delta;
//            //System.out.println("Line 98 MoveDY:" +moveDy);
//        }else if (Game.camera.yPos < this.ypos) {
//            moveDy = -Math.round(1 / speed);
//            //moveDy=moveDy*delta;
//            //System.out.println("Line 101 MoveDy:" +moveDy);
//        }else
//            moveDy = 0;
//        lastPlayerX = Game.camera.xPos;
//        lastPlayerY = Game.camera.yPos;
//        //persuePath();
//
//    }
//
//
//    protected void persuePath(){
//
//        if(lastPlayerX>this.xpos)
//            moveDx=1/speed;
//        else if(lastPlayerX<this.xpos)
//            moveDx=-1/speed;
//        else
//            moveDx=0;
//        if(lastPlayerY>this.ypos)
//            moveDy=1/speed;
//        else if(lastPlayerY<this.ypos)
//            moveDy=-1/speed;
//        else
//            moveDy=0;
//
//    }




}
