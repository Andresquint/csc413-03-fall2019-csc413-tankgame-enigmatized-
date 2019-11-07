package Entity;

import Animation.Texture;

public class Powerup extends Entity {

    public Powerup(double xpos, double ypos, Texture texture){
        super.xPos =xpos;
        super.yPos =ypos;
        super.texture=texture;
    }

    //Why does the default constructuor need to be made for an instance of PowerUp to be used in other packages?
    public Powerup(){}
}
