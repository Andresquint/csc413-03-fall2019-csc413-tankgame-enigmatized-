package Entity;

import java.util.Random;
import Animation.Texture;
import Animation.TextureAnimation;
import Entity.Entity;
import Health.*;

import java.io.IOException;
import Game.*;

public class Enemy extends Movers {

    // coordinages in level position
    public double xpos;
    public double ypos;
    //movement change
    double moveDx;
    double moveDy;




    //ToDo
    //Abstract out health, because wall will need health
    //And you want a way to show dead walls....
    public Health health2;
    double health;

    //Radius
    double width = 0.5;

    // distance to current camera position
    double distanceToCamera;

    double cameraWallDistanceFactor;

    // coordinates transformed by the camera view
    double xTransformed;
    double yTransformed;

    private Texture texture;

    //distance?
    public double distance=0;
    public double centerX;

    //Make this array
    //Use states
    //int states

    public Texture currentTexture;
    protected TextureAnimation currentAnimation;
    protected TextureAnimation walkingAnimation;
    protected TextureAnimation attackAnimation;
    protected TextureAnimation dyingAnimation;




    public Enemy(double xPosition, double yPosition, Texture texture) throws IOException {
        this.xpos = xPosition;
        this.ypos = yPosition;
        this.texture = texture;
        this.health=100;
        this.health2= new Health(100);

        loadAnimations();//Loads animations for a given sprite
        currentAnimation = walkingAnimation;

    }


    public double getDistFromLine(double x1, double y1, double x2, double y2) {

        double A = xpos - x1;
        double B = ypos- y1;
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

        double dx = xpos - xx;
        double dy = ypos - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public double getSideFromLine(double x1, double y1, double x2, double y2) {

        double d = (xpos - x1) * (y2 - y1) - (ypos - y1) * (x2 - x1);
        return d;//Actual value worthless, only concerned with d being negative or postive Postive=Right side of sprite
    }


    public Texture getTexture() {
        return texture;
    }



    public void updateBehavior(double delta) {
        if(alive()) {

           // persue(delta);
            randomMovement();
            updatePlayer(delta);

        }else {
            //Handles the death of the sprite
            moveDy = 0;
            moveDy = 0;
            System.out.println("ARE WE EVER GOING IN HERE?");
            currentAnimation = dyingAnimation;
            //texture=Texture.wood;

//            if(!hasGivenLoot){
//                RaycastEngine.player.addLoot(getLoot());
//                hasGivenLoot = true;
//                RaycastEngine.playSound("sound/Hit.wav");
//            }
            // persuePath();
        }
    }

    public void updatePlayer(double delta) {
        //xpos++;

//        System.out.println("xpos:" +xpos);
//        System.out.println("ypos:" +ypos);
//        System.out.println("dx: " +dx);
//        //Oh the problem with is dx
//        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
//        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));
//        System.out.println( );
        moveDy=Math.floor(moveDx*100)/100;
        moveDy=Math.floor(moveDy*100)/100;
//        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
//        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));
        moveDx=moveDx/10;
        moveDy=moveDy/10;
//        System.out.println("xpos: "+ xpos+"moveDx"+moveDx+"="+" "+(xpos+moveDx));
//        System.out.println("ypos: "+ ypos+"moveDy"+moveDy+"="+" "+(ypos+Math.round(moveDy)));

        double newmoveDx=(moveDx)/(moveDy);//
        double newMoveDy=(moveDy)/1000000000;
        if(alive()){
            try{
            if(!collision( xpos+2, ypos)){

                xpos+=moveDxx;

            }
            }catch (Exception e){
                System.out.println("#1No bueno map out of bounds error");

                }
            try{
            if(!collision(xpos, ypos+2)){
                ypos+=moveDyy;
            }
            }catch (Exception e){
                System.out.println("#2No bueno map out of bounds error");

            }
        }else{
            moveDx = 0;
            moveDy = 0;
        }
    }

    public boolean alive() {
        boolean alive = true;
        if(health<=0)
            alive = false;
        return alive;
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

    public void damaged(int damageTaken) throws IOException {

        System.out.print("Entity.Enemy Health before: "+ this.health);
        this.health-=damageTaken;
        this.health2.damaged(damageTaken);
        System.out.print("Entity.Enemy Health After Hit: "+ this.health);
        if(health<0) {
            texture=Texture.wood;
            textureChange();
            currentAnimation=dyingAnimation;
            animation();
        }//What should the enemy do? Die?
    }



    /**
     * Loads all animations that a sprite will use in its
     * "lifespan".
     */
    protected void loadAnimations() throws IOException {
        walkingAnimation = new TextureAnimation("res/Spider/walkingSpider.png",15);
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
        currentTexture = currentAnimation.Animate();
    }

    public Texture textureChange() throws IOException {
        //Note, I think the problem is that this isn't thread safe.
        //Not 100% on how one would best handle that issue?
        return this.texture= new Texture( "res/3232.png", 16, 16);
    }



}