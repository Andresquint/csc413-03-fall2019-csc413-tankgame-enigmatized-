package Entity;

//

import Animation.Texture;
import Game.Game;
//https://www.piskelapp.com/
import java.io.IOException;

//Animation, Array of Textures. 2d-Cycling through
//2d array state, frames
//Entity needs to make


//Make this proccess Entity before Animation Proccess
//Design ways could
public class Bullet extends Movers {

    boolean playerBullet;
    int damage;
    double speedMult=1.5;

    public Bullet(double xPosition, double yPosition, Texture texture, double xMove, double yMove, boolean playerBullet, int damage) throws IOException {
        super(xPosition, yPosition, texture,  xMove,  yMove);
        this.playerBullet=playerBullet;
        this.damage=damage;

       }

    public void updateBehavior(double delta) {
        double newX = xPos +moveDxx*speedMult;
        double newY = yPos +moveDyy*speedMult;
        //I am a bit confused by this if Statement
        //Does it just remove enemy?
        //I don't think it does that at all.
        //This could be why I am having trouble with printing enemy health
        if(collision(newX, newY)){
            Game.levelInfo.getEnemies().remove(this);
            return;
        }
        xPos =newX;
        yPos =newY;
        for(int i=0; i<Game.levelInfo.getEnemiesListSize(); i++){
            Entity e=Game.levelInfo.getEnemies().get(i);
            //Um huge error here!
            //Check Bullet doesn't do anything
            e.checkBullet(xPos, yPos, damage, playerBullet);


        }

        if(!playerBullet){
            Game.camera.checkBullet(xPos, yPos, damage, playerBullet);

        }

    }





    public void updatePlayer(){}




}
