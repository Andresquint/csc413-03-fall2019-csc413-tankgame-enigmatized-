package Entity;

import Animation.Texture;
import Game.Game;

public class Powerup extends Entity {

    //Powerup State
    boolean on;

    public Powerup(double xpos, double ypos, Texture texture){
        super.xPos =xpos;
        super.yPos =ypos;
        super.texture=texture;
        on=true;

    }

    //Why does the default constructuor need to be made for an instance of PowerUp to be used in other packages?
    public Powerup(){}




    public void updateBehavior(double delta){
        if(on) PowerCollisionCheck();
    }

    //Checks if player is touching power up,
    //If so gives out powerUp
    public void PowerCollisionCheck(){
        if( 1>Math.abs((this.xPos- Game.camera.xPos) )&&  1>Math.abs((this.yPos- Game.camera.yPos))){
            speedUp();
            //Make powerup Unuseable
            this.on=false;
            //
            //Make texture disapear
            try {
                this.texture=new Texture("res/DeadPowerUp.png", 64, 64);
            } catch (Exception e){
                System.out.println("PowerCollsionCheck failed, texture didn't change");
            }

        }
    }

    //Method of its own because maybe other powerups will be made later.
    public void speedUp(){
        Game.camera.MOVE_SPEED=Game.camera.MOVE_SPEED*2;
    }

}
