package Health;

import Animation.Texture;
import Animation.*;




/**
 //I left this here.
 //I wasn't sure to abstract health out.
 //It seem like too much extra crap/over-abstraction
 //I began to, the dropped this
 //Left the code here for possible future use depending on the next game
 */



public class Health {
    double health=100;

    public Health(int sethealth){
        this.health=sethealth;
    }


    public void damaged(int damageTaken) {

        System.out.print("Entity.Enemy Health before: "+ this.health);
        this.health-=damageTaken;
        System.out.print("Entity.Enemy Health After Hit: "+ this.health);
        if(health<0) {


           //Todo
            //Put Textures and animation in a sperate package
           // Then import the package so the below code works
            //currentAnimation=dyingAnimation;
            //animation();
        }//What should the enemy do? Die?
    }


    public double getHealth() {
        double result = ((double)health)/100;
        if(result < 0){
            result = 0;
        }
        return result;
    }


}
