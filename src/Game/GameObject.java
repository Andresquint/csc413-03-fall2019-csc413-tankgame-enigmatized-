package Game;

import Game.Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameObject
{
    public double xPos, yPos;
    public BufferedImage sprite;

    public GameObject(double xPos, double yPos, String spriteLoc) {
        this.xPos = xPos;
        this.yPos = yPos;
        try{
            sprite = ImageIO.read(new File(spriteLoc));
        } catch (IOException e) {
            System.out.println("hwoops");
        }
    }

    public int[] render(Camera c, int[] pixels) {
        double xDist = xPos - c.xPos;
        double yDist = yPos - c.yPos;
        double dist = xDist * c.xDir + yDist * c.yDir;
        if(dist == 0) return pixels;
        int xDirFromCam = (int)Math.signum(xDist);
        double tempY = yPos;
        double tempX = xPos;
        while(xDirFromCam == Math.signum(tempX - c.xPos)) {
            if(Game.map[(int)tempX][(int)tempY] != 0) return pixels;
            tempX -= xDist/(dist*10);
            tempY -= yDist/(dist*10);
        }
        double scale = Math.abs(dist);
        int drawHeight = (int)(Game.screenHeight / scale);
        int drawWidth = (int)(drawHeight * sprite.getWidth() / sprite.getHeight());

        double crossProduct = xDist * c.xDir + yDist * c.yDir;
        if(crossProduct / dist <= 0) return pixels;
        double relCameraAngleX = (xDist * c.yDir - yDist * c.xDir)/dist;

        if(0 <= Math.abs((drawWidth + Game.screenWidth)/2)) Game.g.drawImage(sprite, (int)(relCameraAngleX * Game.screenWidth * 5/6 + Game.screenWidth/2 - drawWidth/2), Game.screenHeight/2 - drawHeight/2, drawWidth, drawHeight, null);
        return pixels;
    }
}