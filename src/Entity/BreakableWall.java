package Entity;

import Animation.Texture;
import RayCasting.MathAssist;

public class BreakableWall extends Entity {

    boolean alive;

    public BreakableWall(double xpos, double ypos, Texture texture){
        super(xpos, ypos, texture);
        super.health=50;
        this.alive=true;
        try {
            this.texture = texture;
        }catch(Exception e){
            System.out.println("BreakAbleWall Texture did note load!!!!!!!");
        }

    }

    public void updateBehavior(double delta){
        if(alive) {}
    }

    public void PowerCollisionCheck(){}

    protected void checkBullet(double xpos, double ypos, int damage, boolean playerBullet) {
        if (playerBullet && MathAssist.distanceBetweenPoints(xpos, ypos, this.xPos, this.yPos) < this.width + 0.1) {
            try {
                damaged(damage);
            }catch(Exception e){
                System.out.println("Bulletcheck For BreakableWall Failed");
            }

        }
    }

    public boolean collides(double xPos, double yPos) {
        return MathAssist.distanceBetweenPoints(xPos, yPos, this.xPos, this.yPos)<this.width*2 && alive;

    }

    public void damaged(int damageTaken)  {

        System.out.print("BreakableWall Health before: "+ this.health);
        this.health-=damageTaken;
        //this.health2.damaged(damageTaken);
        System.out.print("BreakableWall Health After Hit: "+ this.health);
        if(this.health <=0){
            alive=false;
            try {
                this.texture=new Texture("res/DeadPowerUp.png", 64, 64);
            } catch (Exception e){
                System.out.println("BREAKABLEWALL: Texture failed, texture didn't change after health went to zero");
            }
        }
    }



}
