package Entity;

import Animation.Texture;
import Animation.TextureAnimation;
import Game.Game;
import Health.*;

import java.io.IOException;

import RayCasting.MathAssist;

public class Enemy extends Movers {

    // coordinages in level position
//    public double xpos;
//    public double ypos;
    //movement change
    double moveDx;
    double moveDy;
    double xDir;
    double yDir;

    boolean forward=true;
    boolean back=false;
    boolean right=false;
    boolean left=true;
    boolean shooting=false;

    double MOVE_SPEED=0.16;
    double ROTATION_SPEED=0.095;

    double lastShot;
    double nextShot=1.5;
    double shootDistance=2.0;






    //ToDo
    //Abstract out health, because wall will need health
    //And you want a way to show dead walls....
    public Health health2;
    //No, you can have all the health functions and damage functions in Entity class
    //Then you can wrap it around another method with more specific implementation in each class if need be.
    //But to save some time.
    //You should make each Entity State based


    // distance to current camera position
    double distanceToCamera;

    double cameraWallDistanceFactor;

    // coordinates transformed by the camera view
    double xTransformed;
    double yTransformed;



    //distance?
//    public double distance=0;
//    public double centerX;

    //Make this array
    //Use states
    //int states

    public Texture currentTexture;
    public TextureAnimation currentAnimation;
    protected TextureAnimation walkingAnimation;
    protected TextureAnimation attackAnimation;
    protected TextureAnimation dyingAnimation;
    protected TextureAnimation seezure;




    public Enemy(double xPosition, double yPosition, Texture texture) throws IOException {
        super(xPosition, yPosition, texture, 0, 0);

        //this.texture = texture;
        this.health=100;
        this.health2= new Health(100);
        xDir=1;


        loadAnimations();//Loads animations for a given sprite
        //currentAnimation = walkingAnimation;

    }


    protected void checkBullet(double xpos, double ypos, int damage, boolean playerBullet) {
        if (playerBullet && MathAssist.distanceBetweenPoints(xpos, ypos, this.xPos, this.yPos)<this.width+0.1) {
            damaged(damage);
        }
    }




    public void updateBehavior(double delta) {


        if(alive()) {

           // persue(delta);
          //  randomMovement();

            updatePlayer(delta);
            updatepostions(Game.level.map);

        }else {
            //Handles the death of the sprite
            moveDy = 0;
            moveDy = 0;
            System.out.println("ARE WE EVER GOING IN HERE?");

            texture= seezure.Animate();


                //animation();

        }





    }

    public void updatePlayer(double delta) {
        double tXpos=xPos+(xDir*10);
        double tYpos=yPos+(yDir*10);
       // System.out.println(delta);
        lastShot+=delta;
        if(Game.camera.getSideFromLine(xPos, yPos, tXpos, tYpos)<0){
            left=true;
            right =false;

        }else{
            left=false;
            right=true;
        }
        if(Game.camera.getDistFromLine(xPos, yPos, tXpos, tYpos)<shootDistance){
            shooting=true;
        }else shooting=false;




        //xpos++;

//        System.out.println("xpos:" +xpos);
//        System.out.println("ypos:" +ypos);
//        System.out.println("dx: " +dx);
//        //Oh the problem with is dx
//        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
//        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));
//        System.out.println( );
//        moveDy=Math.floor(moveDx*100)/100;
//        moveDy=Math.floor(moveDy*100)/100;
////        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
////        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));
//        moveDx=moveDx/10;
//        moveDy=moveDy/10;
////        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
////        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));
//
//        double newmoveDx=(moveDx)/(moveDy);//
//        double newMoveDy=(moveDy)/1000000000;
//        if(alive()){
//            try{
//            if(!collision( xPos +2, yPos)){
//
//                xPos +=moveDxx;
//
//            }
//            }catch (Exception e){
//                System.out.println("#1No bueno map out of bounds error");
//
//                }
//            try{
//            if(!collision(xPos, yPos +2)){
//                yPos +=moveDyy;
//            }
//            }catch (Exception e){
//                System.out.println("#2No bueno map out of bounds error");
//
//            }
//        }else{
//            moveDx = 0;
//            moveDy = 0;
//        }
    }


    public double getHealth() {
        double result = ((double)health)/100;
        if(result <= 0){
            result = 0.01;//Returns 1 instead of zero because the way enemy health bar rendering is
        }               //An Out of Bounds error gets thrown if zero
        return result;
    }


    public void damaged(int damageTaken)  {

        System.out.print("Entity.Enemy Health before: "+ this.health);
        this.health-=damageTaken;
        //this.health2.damaged(damageTaken);
        System.out.print("Entity.Enemy Health After Hit: "+ this.health);

    }



    /**
     * Loads all animations that a sprite will use in its
     * "lifespan".
     */
    protected void loadAnimations() throws IOException {
        seezure = new TextureAnimation("s", "r", 3,  5, true);
       // attackAnimation = new Animation.TextureAnimation("res/MechCon.png", 1);
        dyingAnimation = new TextureAnimation("res/Spider/deadSpider.png",1, 5,false);
    }

    /**
     * Consults the animation and sets the next frame
     * of the walking animation to advance by one. Is
     * designed to be called continuously every 1/60th
     * of a second.
     */
    protected void animation(){
        this.texture = walkingAnimation.Animate();
    }

    public Texture textureChange() throws IOException {
        //Note, I think the problem is that this isn't thread safe.
        //Not 100% on how one would best handle that issue?
        return this.texture= new Texture( "res/3232.png", 16, 16);
    }



    public void updatepostions(int[][] map){
        if(shooting&& lastShot>nextShot){
            lastShot=0;

            try {
                attack();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos +=xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
                yPos +=yDir*MOVE_SPEED;
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


        }
        if(left) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);


        }
    }

    public void attack() throws IOException {


        Game.playSound("sound/Shoot.wav");
        Game.levelInfo.getEnemies().add(new Bullet(this.xPos, this.yPos, new Texture("res/bullet.png.png", 64), xDir, yDir, false, 10));

    }


    }