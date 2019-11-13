package Game;

import Animation.Texture;
import Entity.Bullet;
import Game.Game;
import RayCasting.MathAssist;
import Weapons.Weapon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

//https://github.com/DanielAlpizar/POO-Mycar/tree/fdbe6c7f553871890a7586be76fe39bbb87c3758/CarSimulator/src/Manager3D


public class Camera implements KeyListener{
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;
    public boolean shooting=false;
    Weapon currentWeapon;


    //Players health and gold
    private int health=100;
    private int gold;

    //Pplayer Game stats
    double nextShotTime=0;
    double shotTimeDelta=606863000;



    public Camera(double x, double y, double xd, double yd, double xp, double yp) throws IOException {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;


        health=100;
        currentWeapon = loadFist(); //Sets the default weapon
        currentWeapon.animation();
    }

    private void loadWeapons() throws IOException {
        //Initializes all the weapons below
        Weapon fist = new Weapon("res/Weapons/fist.png", 2,(int)Math.round(.33*Game.screenWidth), (int)Math.round(.66*Game.screenWidth), 15, 2);

    }

    private Weapon loadFist() throws IOException {
        //Initializes all the weapons below
        return new Weapon("res/Weapons/crossbow.png", 1,(int)Math.round(.20*Game.screenWidth), (int)Math.round(.80*Game.screenWidth), 50, 10.0);
        //Weapons.Weapon fist = new Weapons.Weapon("res/Weapons/fist.png", 2,(int)Math.round(.33*Game.Game.screenWidth), (int)Math.round(.66*Game.Game.screenWidth), 15, 2);
    }


    public void keyPressed(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = true;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = true;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = true;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = true;
        if((key.getKeyCode() == KeyEvent.VK_SPACE)) {
            shooting = true;
//            try {
//                attack();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
    public void keyReleased(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = false;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = false;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = false;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = false;
        if((key.getKeyCode() == KeyEvent.VK_SPACE))
            shooting = false;
    }
    public void update(int[][] map, long now) {
        if(shooting && now>= nextShotTime){
            try {
                attack();
                System.out.println(now);
            }catch(Exception e){
                System.out.println(e);
            }
            nextShotTime=now+shotTimeDelta;

        }
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos+=xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
                yPos+=yDir*MOVE_SPEED;
        }
        if(back) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
                xPos-=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
                yPos-=yDir*MOVE_SPEED;
        }
        if(right) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(left) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
    }
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    public double getHealth() {
        double result = ((double)health)/100;
        if(result < 0){
            result = 0;
        }
        return result;
    }


    public void attack() throws IOException {


        Game.playSound("sound/Shoot.wav");
        Game.levelInfo.getEnemies().add(new Bullet(this.xPos, this.yPos, new Texture("res/bullet.png.png", 64), xDir, yDir, true, 50));




//        for(int i=0; i<Game.levelInfo.getEnemiesListSize(); i++){
//            //Checks to make sure if they are opening a chest or not
//
//                //ensures the player is close enough
//                if(Game.levelInfo.getEnemyFromEnemyList(i).distance<1){
//                   // System.out.println("Souldn't we be getting here?");
//                    if((Game.levelInfo.getEnemyFromEnemyList(i).centerX>currentWeapon.getLeftRect()) && (Game.levelInfo.getEnemyFromEnemyList(i).centerX<currentWeapon.getRightRect())){
//                        System.out.println("Do we get herer !!!!!!!!!!!!!!!!!!!!!!!!");
//                        Game.levelInfo.getEnemyFromEnemyList(i).damaged(currentWeapon.getDamage());
//
//                       // Game.Game.playSound("sound/ExplosionWarning.wav");
//                    }
//                }
//            else{
//                //Checks range and attacks
//                if(Game.levelInfo.getEnemyFromEnemyList(i).distance<currentWeapon.getWeaponRange()){
//                    if((Game.levelInfo.getEnemyFromEnemyList(i).centerX>currentWeapon.getLeftRect()) && (Game.levelInfo.getEnemyFromEnemyList(i).centerX<currentWeapon.getRightRect())){
//                        Game.levelInfo.getEnemyFromEnemyList(i).damaged(currentWeapon.getDamage());
//                    }
//                }
//            }
//        }

    }

    public void checkBullet(double xpos, double ypos, int damage, boolean playerBullet) {
        if (playerBullet && MathAssist.distanceBetweenPoints(xpos, ypos, this.xPos, this.yPos)<0.4) {
            //damaged(damage);
        }
    }
  //reworked the entire project

    public double getDistFromLine(double x1, double y1, double x2, double y2) {

        double A = xPos - x1;
        double B = yPos - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = -1;
        if (len_sq != 0) //in case of 0 length line
            param = dot / len_sq;

        double xx, yy;

        if (param < 0) {
            xx = x1;
            yy = y1;
        }
        else if (param > 1) {
            xx = x2;
            yy = y2;
        }
        else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }

        double dx = xPos - xx;
        double dy = yPos - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public double getSideFromLine(double x1, double y1, double x2, double y2) {

        double d = (xPos - x1) * (y2 - y1) - (yPos - y1) * (x2 - x1);
        return d;//Actual value worthless, only concerned with d being negative or postive Postive=Right side of sprite
    }


}