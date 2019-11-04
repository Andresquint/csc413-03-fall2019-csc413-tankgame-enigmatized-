
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

//https://github.com/DanielAlpizar/POO-Mycar/tree/fdbe6c7f553871890a7586be76fe39bbb87c3758/CarSimulator/src/Manager3D


public class Camera implements KeyListener{
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;
    public boolean shooting=false;
    Weapon currentWeapon;


    //Players health and gold
    private int health;
    private int gold;

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
        return new Weapon("res/Weapons/crossbow.png", 1,(int)Math.round(.20*Game.screenWidth), (int)Math.round(.80*Game.screenWidth), 50, 5.0);
        //Weapon fist = new Weapon("res/Weapons/fist.png", 2,(int)Math.round(.33*Game.screenWidth), (int)Math.round(.66*Game.screenWidth), 15, 2);
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
            attack();
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
    public void update(int[][] map) {
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


    public void attack() {
        Game.playSound("sound/Shoot.wav");
    }



}