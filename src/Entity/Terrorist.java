package Entity;

import Animation.Texture;
import Animation.TextureAnimation;
import Entity.Enemy;

import java.io.IOException;

public class Terrorist extends Enemy {


    public Terrorist(double xPosition, double yPosition, Texture texture) throws IOException {
        super(xPosition, yPosition, texture);
    }


    protected void loadAnimations() throws IOException {

        dyingAnimation = new TextureAnimation("res/Spider/deadSpider.png",1, 5,false);//example death animation
    }

}
